package edu.vt.cs.metrics;
import edu.vt.cs.sam.SAMException;
import edu.vt.cs.sam.readers.*;
import edu.vt.cs.sam.handlers.ReuseMetricsHandler;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Calculates reuse metrics Internal & External Reuse Level and Internal & External
 * Reuse Frequency. Uses call flow for calculating T for [I|E]RL and total RF
 * for [I|E]RF. To change the way that T is calculated override either calculateTForRF
 * or calculateTForRL
 * @author adam
 */
public class CReuseMetricsReporter {

    protected static boolean recursiveReporting = false;
    // Internal Threshold Level
    protected static int itl = 1;
    // External Threshold Level
    protected static int etl = 0;
    
    public static void main(String[] args) {
        if(args.length == 0) {
            printUsageMessage();
            return;
        }
        String fileArgument = null;
        try {
             fileArgument = parseArguments(args);
        } catch (InvalidArgumentsException ex) {
            printUsageMessage();
            return;
        }
        File toParse = new File(fileArgument);
        if(!toParse.exists()) {
            System.err.println(toParse.getAbsolutePath() + " does not exist");
            return;
        }
        if(!toParse.canRead()) {
            System.err.println("Cannot read " + toParse.getAbsolutePath() + " please " +
                    "make sure that you have read privileges on the file");
        }
        ReuseMetricsHandler handler = new ReuseMetricsHandler();
        ArrayList<Pattern> toIgnore = new ArrayList<Pattern>();
        toIgnore.add(Pattern.compile("/usr(?:[^/]*/)+include(?:/[^/]*)*"));
        handler.setDisregardFilePatterns(toIgnore);
        CPPReader reader = new CPPReader(handler);
        printReportHeader();
        reportOnFile(reader, toParse);        
    }

    protected static int getThresholdArgument(String arg) throws NumberFormatException, InvalidArgumentsException {
        int retVal = 0;
        int colonPos = arg.indexOf(":");
        if (colonPos < 0) {
            throw new InvalidArgumentsException("Missing ':' in " + arg);
        }
        retVal = Integer.parseInt(arg.substring(colonPos + 1).trim());
        return retVal;
    }

    protected static String parseArguments(String[] args) throws InvalidArgumentsException {
        String retVal = null;
        for(int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if(arg.toLowerCase().startsWith("-itl")) {
                itl = getThresholdArgument(arg);
            } else if(arg.toLowerCase().startsWith("-etl")) {
                etl = getThresholdArgument(arg);
            } else if(i != args.length - 1) {
                throw new InvalidArgumentsException("Do not understand argument " + arg);
            } else {
                retVal = arg;
            }
        }
        return retVal;
    }
    protected static void printUsageMessage() {
        System.out.println("Usage: " + CReuseMetricsReporter.class.getName() + 
                "[-ITL:internalReuseThreshold] [-ETL:externalReuseThreshold] " +
                "FileToParse");
    }    
    
    protected static void reportOnFile(CPPReader reader, File toParse) {
        if(toParse.isDirectory()) {
            recursiveReporting = true;
            for(File file : toParse.listFiles()) {
                reportOnFile(reader, file);
            }
        } else {
            try {
                reader.parse(toParse.getAbsolutePath());
                report(toParse, (ReuseMetricsHandler) reader.getCodeHandler());
            } catch (SAMException ex) {
                System.err.println("Error reading file " + toParse.getAbsolutePath() + 
                        (recursiveReporting ? ", run the report on just that file for more details" 
                        : ex.getCause().getMessage()));
            }
        }
    }
    
    protected static void printReportHeader() {
        System.out.println("File, Internal Reuse Level, External Reuse Level, Internal Reuse Frequency, External Reuse Frequency");
    }
    
    /**
     * Actually runs the report, using calculateTForRL and calculateTForRF to determine
     * T
     * @param parsedFile
     * @param handler
     */
    protected static void report(File parsedFile, ReuseMetricsHandler handler) {        
        int numLowerLevelItems = calculateTForRL(handler);
        int totalFrequency = calculateTForRF(handler);
        ArrayList<String> itemsFromProgramFlow = calculateProgramFlow(handler);
        HashMap<String, Integer> internallyReusedCounts = new HashMap<String, Integer>();
        HashMap<String, Integer> externallyReusedCounts = new HashMap<String, Integer>();
        
        for(String methodCalled : itemsFromProgramFlow) {
            boolean isInternal = handler.getInternallyReusedCount().containsKey(methodCalled);
            HashMap<String, Integer> target = isInternal ?
                internallyReusedCounts : externallyReusedCounts;
//            int count = target.containsKey(methodCalled) ? target.get(methodCalled) : 0;
            target.put(methodCalled, isInternal ? handler.getInternallyReusedCount().get(methodCalled) : 
                handler.getExternallyReusedCount().get(methodCalled));
        }

        StringBuilder output = new StringBuilder().append(parsedFile.getName()).append(",");        
        output.append(
                calculateReuseLevel(
                   handler.getMethodReuseMapping(), 
                   internallyReusedCounts, itl, numLowerLevelItems)
                ).append(",");        
        output.append(
                   calculateReuseLevel(
                      handler.getMethodReuseMapping(), 
                      externallyReusedCounts, 
                      etl, numLowerLevelItems
                   )
                ).append(",")
                .append(
                   calculateReuseFrequency(
                      internallyReusedCounts, 
                      totalFrequency,
                      itl
                   )                   
                ).append(",")
                .append(
                   calculateReuseFrequency(
                      externallyReusedCounts,
                      totalFrequency,
                      etl
                   )
                );
        System.out.println(output);
    }
    
    /**
     * Override this method to implement a different mechanism for calculating T
     * for use in the RL divisor; currently uses a program flow analysis from
     * the "main" method, similar to running cflow program_file | wc -l
     * @param handler
     * @return
     */
    protected static int calculateTForRL(ReuseMetricsHandler handler) {
        return calculateTViaProgramFlow(handler);
    }
    
    /**
     * Override this method to implement a different mechanism for calculating T
     * for use in the RF divisor; currently finds the total reuse frequency
     * @param handler
     * @return
     */
    protected static int calculateTForRF(ReuseMetricsHandler handler) {
        ArrayList<String> programFlowItems = calculateProgramFlow(handler);
        HashSet<String> addedItems = new HashSet<String>();
        int t = 0;
        HashMap<String, Integer> internal = handler.getInternallyReusedCount();
        HashMap<String, Integer> external = handler.getExternallyReusedCount();
        for(String item : programFlowItems) {
            if(addedItems.contains(item)) {
                continue;
            }
            addedItems.add(item);
            if(internal.containsKey(item)) {
                t += internal.get(item);
            } else if(external.containsKey(item)) {
                t += external.get(item);
            }
        }
        return t;
    }
    
    protected static ArrayList<String> calculateProgramFlow(ReuseMetricsHandler handler) {
        ArrayList<String> retVal = new ArrayList<String>();
        HashMap<String, HashSet<String>> reusedMethods = handler.getMethodReuseMapping();
        ArrayList<String> workingSet = new ArrayList<String>();
        if(reusedMethods.containsKey("main")) {
            retVal.add("main");
            workingSet.addAll(reusedMethods.get("main"));
        } else if(reusedMethods.containsKey("WinMain")) {
            retVal.add("WinMain");
            workingSet.addAll(reusedMethods.get("WinMain"));
        } else {
            workingSet.addAll(new HashSet<String>(handler.getInternallyReusedCount().keySet()));
        }
        HashMap<String, HashSet<String>> descendedMapping = new HashMap<String, HashSet<String>>();
        while(!workingSet.isEmpty()) {
            String method = workingSet.remove(0);
            retVal.add(method);
            
            if(reusedMethods.containsKey(method)) {
                HashSet<String> toAdd = new HashSet<String>(reusedMethods.get(method));
                /*
                 * Jumping through these hoops to avoid loops in call chains
                 */
                HashSet<HashSet<String>> ancestors = new HashSet<HashSet<String>>();
                for(Map.Entry<String, HashSet<String>> descendentMap : descendedMapping.entrySet()) {
                    if(descendentMap.getValue().contains(method)) {
                        toAdd.remove(descendentMap.getKey());
                        ancestors.add(descendentMap.getValue());
                    }
                }
                for(HashSet<String> ancestor : ancestors) {
                    ancestor.addAll(toAdd);
                }
                if(!descendedMapping.containsKey(method)) {
                    descendedMapping.put(method, toAdd);
                }
                workingSet.addAll(toAdd);
            }
        }
        return retVal;
    }
    
    protected static int calculateTViaProgramFlow(ReuseMetricsHandler handler) {
        HashSet<String> temp = new HashSet<String>(calculateProgramFlow(handler));
        int mainModifier = temp.contains("main") || temp.contains("WinMain") ? 1 : 0;
        return temp.size() - mainModifier;
    }
    
    protected static int calculateTotalReuseFrequency(HashMap<String, Integer> internal, HashMap<String, Integer> external) {
        int retVal = 0;
        for(Map.Entry<String, Integer> entry : internal.entrySet()) {
            int count = entry.getValue();            
            retVal += count;
        }
        for(Map.Entry<String, Integer> entry : external.entrySet()) {
            int count = entry.getValue();
            retVal += count;
        }
        return retVal;
    } 
    
    protected static float calculateReuseFrequency(HashMap<String, Integer> reuseCount, int numLowerLevelItems, int threshold) {
        float numerator = 0.0F;
        for(String method : reuseCount.keySet()) {
            Integer count = reuseCount.get(method);            
            if(count > threshold) {
                numerator += count.floatValue();
                System.err.println(method + " accounts for " + count);
            } else {
                System.err.println("Rejecting " + method);
            }
        }
        return numerator / (float) numLowerLevelItems;
    }
    
    protected static int calculateNumberLowerLevelItems(HashMap<String, Integer> internalReuseCount,
            HashMap<String, Integer> externalReuseCount, HashMap<String, HashSet<String>> definedMethods, int itl, int etl) {
        int retVal = 0;
        ArrayList<String> usedInT = new ArrayList<String>();
        usedInT.addAll(definedMethods.keySet());
        HashSet<String> counted = new HashSet<String>();
        int mainAppearances = 0;
        for(HashSet<String> methods : definedMethods.values()) {
            retVal += methods.size();
            usedInT.addAll(methods);
            counted.addAll(methods);
            if(methods.contains("main")) {
                ++mainAppearances;
            }
        }        
        HashSet<String> allDefined = new HashSet<String>(definedMethods.keySet());
        allDefined.removeAll(counted);
        allDefined.remove("main");
        retVal += allDefined.size();
        retVal -= mainAppearances;
        return retVal;
    }
    
    protected static int calculateAcceptedReusedItems(HashMap<String, Integer> methodCounts, int threshold) {
        int retVal = 0;
        for(Map.Entry<String, Integer> methodEntry : methodCounts.entrySet()) {
            if(methodEntry.getValue() > threshold) {
                ++retVal;
            }
        }
        return retVal;
    }
    
    protected static float calculateReuseLevel(HashMap<String, HashSet<String>> methodReuseMapping, HashMap<String, Integer> acceptedMethods, 
            int reuseThreshold, int numLowerLevelItems) {
        // casting this to float since otherwise the metrics would always come out 0
        float divisor = (float) numLowerLevelItems;
        float numReusedItems = 0.0F;
        for(String acceptedMethod : acceptedMethods.keySet()) {
            if(acceptedMethods.get(acceptedMethod) > reuseThreshold) {
                numReusedItems += 1.0F;
            }
        }
        return numReusedItems / divisor;
    }
    
    protected static class InvalidArgumentsException extends Exception {
        public InvalidArgumentsException(String message) {
            super(message);
        }
    }
}
