package edu.vt.cs.sam.handlers;

import edu.vt.cs.sam.CodeHandler;

/**
 *
 * @author adam
 */
public interface FileAwareCodeHandler extends CodeHandler {
    public void startFile(String fileName);
    public void endFile();
}
