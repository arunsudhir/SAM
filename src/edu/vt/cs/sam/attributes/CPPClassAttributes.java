/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.vt.cs.sam.attributes;

/**
 *
 * @author adam
 */
public class CPPClassAttributes /*extends JavaClassAttributes */{
    private boolean isUnion = false;
    private boolean isStruct = false;
    private boolean isClass = false;

    public boolean isUnion() {
        return isUnion;
    }

    public void setUnion(boolean isUnion) {
        this.isUnion = isUnion;
    }

    public boolean isStruct() {
        return isStruct;
    }

    public void setStruct(boolean isStruct) {
        this.isStruct = isStruct;
    }

    public boolean isClass() {
        return isClass;
    }

    public void setClass(boolean isClass) {
        this.isClass = isClass;
    }
}
