/* Generated By:JJTree: Do not edit this line. ASTvoid.java */

package edu.vt.cs.sam.readers;

public class ASTvoid extends SimpleNode {
  public ASTvoid(int id) {
    super(id);
  }

  public ASTvoid(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}