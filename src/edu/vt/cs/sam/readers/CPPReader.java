
package edu.vt.cs.sam.readers;

import edu.vt.cs.sam.CodeHandler;
import edu.vt.cs.sam.SAMException;
import edu.vt.cs.sam.handlers.FileAwareCodeHandler;
import java.io.IOException;

/**
 *
 * @author adam
 */
public class CPPReader implements edu.vt.cs.sam.CodeReader {

    private CodeHandler handler;
    
    public CPPReader(CodeHandler handler) {
        if(null == handler) {
            throw new IllegalArgumentException("Need a non-null CodeHandler to receive events");
        }
        this.handler = handler;
    }
    
    protected CPPReader() {
        /* Only here for subclasses */
    }
    
    /**
     * This method need not be called unless using a subclass of CPPReader that 
     * does not mandate a CodeHandler in its constructor
     * @param handler
     */
    public void setCodeHandler(CodeHandler handler) {
        this.handler = handler;
    }

    public CodeHandler getCodeHandler() {
        return handler;
    }
    
    /** 
     * Parses a C or C++ file, passing its events to the handler set in setCodeHandler.
     * If the handler implements FileAwareCodeHandler, this will call the startFile
     * and endFile methods immediately before and after parsing begins. If an
     * exception is thrown during the parsing phase, the root cause is stored in
     * the thrown SAMException, and is available via Throwable's getCause() method
     * @param fileToParse
     * @throws edu.vt.cs.sam.SAMException
     */
    public void parse(String fileToParse) throws SAMException {
        try {
            if(handler instanceof FileAwareCodeHandler) {
                ((FileAwareCodeHandler) handler).startFile(fileToParse);
            }
            CPPParser.parse(fileToParse, handler);
            if(handler instanceof FileAwareCodeHandler) {
                ((FileAwareCodeHandler) handler).endFile();
            }
        } catch (IOException ex) {
            SAMException toThrow = new SAMException();
            toThrow.initCause(ex);
            throw toThrow;
        } catch (ParseException ex) {
            SAMException toThrow = new SAMException();
            toThrow.initCause(ex);
            throw toThrow;
        }
    }
}
