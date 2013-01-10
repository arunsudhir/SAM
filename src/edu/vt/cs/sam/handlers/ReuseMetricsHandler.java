package edu.vt.cs.sam.handlers;

import edu.vt.cs.sam.attributes.JavaClassAttributes;
import edu.vt.cs.sam.attributes.JavaLineAttributes;
import edu.vt.cs.sam.attributes.JavaMethodAttributes;
import java.util.*;
import java.util.regex.*;

public class ReuseMetricsHandler implements FileAwareCodeHandler {

    private HashSet<String> internallyDefinedMethods;
    private HashSet<String> externallyDefinedMethods;
    private HashMap<String, Integer> internallyReusedCount = new HashMap<String, Integer>();
    private HashMap<String, Integer> externallyReusedCount = new HashMap<String, Integer>();
    private HashMap<String, HashSet<String>> methodReuseMapping = new HashMap<String, HashSet<String>>();
    private int statementCount = 0;
    private String currentFile = null;
    private String currentMethodName = null;
    private boolean inInternalDefinitions = true;
    private boolean inIgnoredFile = false;
    private ArrayList<Pattern> disregardFilePatterns = new ArrayList<Pattern>();
    private ArrayList<Pattern> disregardMethodCalls = new ArrayList<Pattern>();
    private static final Pattern methodCallPattern = Pattern.compile("((?:\\p{Alpha}|_)\\w*)\\s*\\(");
    private static final Pattern PREPROC_FILENAME_PATTERN = Pattern.compile("\"([^\"]+)\"");    
    
    public ReuseMetricsHandler() {
        for(String str : new String[] {"auto", "break", "case", "char", "const", 
            "continue", "default", "do", "double", "else", "enum", "extern", 
            "float", "for", "goto", "if", "int", "long", "register", "return", 
            "short", "signed", "sizeof", "static", "struct", "switch", "typedef", 
            "union", "unsigned", "void", "volatile", "while"} ) {
            disregardMethodCalls.add(Pattern.compile(str));
        }        
    }
    
    public void startFile(String fileName) {
        currentFile = getBaseFileName(fileName);
        internallyDefinedMethods = new HashSet<String>();
        externallyDefinedMethods = new HashSet<String>();
    }

    public void addIgnoreMethod(Pattern methodPattern) {
        disregardMethodCalls.add(methodPattern);
    }
    
    public void endFile() {
    }

    public void startProject() {        
    }

    public void endProject() {
    }

    public void startClass(JavaClassAttributes arg0) {
    }

    public void endClass(JavaClassAttributes arg0) {
    }

    public void startMethod(JavaMethodAttributes arg0) {
        if(inIgnoredFile) {
            return;
        }
        if(inInternalDefinitions) {
            internallyDefinedMethods.add(arg0.getMethodName());
            if(!internallyReusedCount.containsKey(arg0.getMethodName())) {
                internallyReusedCount.put(arg0.getMethodName(), 0);
            }
            externallyDefinedMethods.remove(arg0.getMethodName());            
        } else {            
            if(!internallyDefinedMethods.contains(arg0.getMethodName())) {
                externallyDefinedMethods.add(arg0.getMethodName());
            }        
        }        
        // allows us to avoid counting recursion as reuse
        currentMethodName = arg0.getMethodName();
    }

    public void endMethod(JavaMethodAttributes arg0) {
        currentMethodName = "STATIC";
    }

    public void endLine(JavaLineAttributes arg0) {
        String type = arg0.getLineType();
        if("preprocessor_output".equals(type)) {
            Matcher matcher = PREPROC_FILENAME_PATTERN.matcher(arg0.getLineText());            
            if(matcher.find()) {
                String path = matcher.group(1);
                inIgnoredFile = false;
                for(Pattern ignorePattern : disregardFilePatterns) {
                    if(ignorePattern.matcher(path).matches()) {
                        inIgnoredFile = true;
                        break;
                    }
                }
                inInternalDefinitions = currentFile.equalsIgnoreCase(getBaseFileName(matcher.group(1)));                
            }
        } else if("comment".equals(type)) {
            /*  Do nothing... we don't care about comments for this metric */
        } else if("function_declarator".equals(type)) {
            /* We'll catch these in the startMethod event */
        } else if(type.contains("statement") || type.contains("expression") || type.contains("declaration")) {
            ArrayList<String> methodCalls = findMethodCalls(arg0.getLineText());
            for(String methodCall : methodCalls) {
                if(methodCall.equals(currentMethodName)) {
                    // don't count recursion as reuse
                    continue;
                }
                boolean skipMethod = false;
                for(Pattern disregardedMethod : disregardMethodCalls) {
                    if(disregardedMethod.matcher(methodCall).matches()) {
                        skipMethod = true;
                        break;
                    }
                }
                if(skipMethod) {
                    continue;
                }
                ++statementCount;
                if(internallyDefinedMethods.contains(methodCall)) {
                    incrementCount(internallyReusedCount, methodCall);
                } else if(!inIgnoredFile && !currentMethodName.equals("STATIC")) {
                    incrementCount(externallyReusedCount, methodCall);
                }
                HashSet<String> temp = methodReuseMapping.containsKey(currentMethodName) ? methodReuseMapping.get(currentMethodName) : new HashSet<String>();
                temp.add(methodCall);
                methodReuseMapping.put(currentMethodName, temp);
            }
        }
    }

    public int getStatementCount() {
        return statementCount;
    }
    private void incrementCount(Map<String, Integer> countMap, String key) {
        for(Pattern pattern : disregardMethodCalls) {
            if(pattern.matcher(key).matches()) {
                return;
            }
        }
        int count = countMap.containsKey(key) ? countMap.get(key).intValue() : 0;
        ++count;
        countMap.put(key, Integer.valueOf(count));
        String methodName = currentMethodName != null ? currentMethodName : "STATIC";
        HashSet<String> reusedMethods = getMethodReuseMapping().containsKey(methodName) ? 
            getMethodReuseMapping().get(methodName) : new HashSet<String>();
        reusedMethods.add(key);
        getMethodReuseMapping().put(methodName, reusedMethods);
    }
    
    protected static ArrayList<String> findMethodCalls(String line) {
        ArrayList<String> retVal = new ArrayList<String>();
        if(line == null || line.trim().length() == 0) {
            return retVal;
        }
        Matcher matcher = methodCallPattern.matcher(line);
        int start = 0;
        while(matcher.find(start)) {
            start = matcher.end();
            String methodCall = matcher.group(1);
            retVal.add(matcher.group(1));
        }
        return retVal;
    }    

    public ArrayList<Pattern> getDisregardFilePatterns() {
        return disregardFilePatterns;
    }

    public void setDisregardFilePatterns(ArrayList<Pattern> disregardFilePatterns) {
        this.disregardFilePatterns = disregardFilePatterns;
    }

    protected String getBaseFileName(String name) {
        String retVal = name;
        while (retVal.endsWith("/") || retVal.endsWith("\\")) {
            retVal = retVal.substring(0, retVal.length() - 1);
        }
        retVal = retVal.replaceAll("\\.\\w*$", "");
        int slashPos = -1;
        while ((-1 != (slashPos = retVal.lastIndexOf("/"))) || (-1 != (slashPos = retVal.lastIndexOf("\\")))) {
            retVal = retVal.substring(slashPos + 1);
        }
        return retVal;
    }

    public HashMap<String, Integer> getInternallyReusedCount() {
        return internallyReusedCount;
    }

    public HashMap<String, Integer> getExternallyReusedCount() {
        return externallyReusedCount;
    }

    public HashMap<String, HashSet<String>> getMethodReuseMapping() {
        return methodReuseMapping;
    }
}
