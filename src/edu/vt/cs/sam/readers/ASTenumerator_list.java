/* Generated By:JJTree: Do not edit this line. ASTenumerator_list.java */

package edu.vt.cs.sam.readers;

public class ASTenumerator_list extends SimpleNode {
  public ASTenumerator_list(int id) {
    super(id);
  }

  public ASTenumerator_list(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
