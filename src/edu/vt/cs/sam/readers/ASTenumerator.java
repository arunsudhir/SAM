/* Generated By:JJTree: Do not edit this line. ASTenumerator.java */

package edu.vt.cs.sam.readers;

public class ASTenumerator extends SimpleNode {
  public ASTenumerator(int id) {
    super(id);
  }

  public ASTenumerator(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
