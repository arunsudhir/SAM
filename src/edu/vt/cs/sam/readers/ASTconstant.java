/* Generated By:JJTree: Do not edit this line. ASTconstant.java */

package edu.vt.cs.sam.readers;

public class ASTconstant extends SimpleNode {
  public ASTconstant(int id) {
    super(id);
  }

  public ASTconstant(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
