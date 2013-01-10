/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.vt.cs.sam.handlers;

import edu.vt.cs.sam.CodeHandler;
import edu.vt.cs.sam.attributes.JavaClassAttributes;
import edu.vt.cs.sam.attributes.JavaLineAttributes;
import edu.vt.cs.sam.attributes.JavaMethodAttributes;
/**
 *
 * @author adam
 */
public class SimpleHandler implements CodeHandler {

    int indentation = 0;
    public void startProject() {
        System.out.println("Starting project...");
    }

    public void endProject() {
        System.out.println("Finished with project...");
    }

    public void startClass(JavaClassAttributes arg0) {        
        System.out.println(getIndentation() + arg0.getClassModifier() + " " + arg0.getClassName() + 
                " extends " + arg0.getExtendsList());
        indentation += 3;
    }

    public void endClass(JavaClassAttributes arg0) {        
        indentation -= 3;
        System.out.println(getIndentation() + "Finished " + arg0.getClassName());
        
    }

    public void startMethod(JavaMethodAttributes arg0) {
        
        System.out.println(getIndentation() + "Starting " + arg0.getMethodReturnType() + " " + 
                arg0.getMethodName() + " (" + arg0.getParameterList() + 
                ") throws (" + arg0.getExceptionList() + ")");
        indentation += 3;
    }

    public void endMethod(JavaMethodAttributes arg0) {                
        indentation -= 3;
        System.out.println(getIndentation() + "Finished " + arg0.getMethodName());
    }

    public void endLine(JavaLineAttributes arg0) {
        System.out.println(getIndentation() + "Line: " + arg0.getLineNumber() + " is of type " + 
                arg0.getLineType() + " with text " + arg0.getLineText());
    }

    protected String getIndentation() {
        StringBuilder retVal = new StringBuilder();
        for(int i = 0; i < indentation; ++i) {
            retVal.append(" ");
        }
        return retVal.toString();
    }
}
