/* Generated By:JJTree: Do not edit this line. ASTptr_operator.java */

package edu.vt.cs.sam.readers;

public class ASTptr_operator extends SimpleNode {
  public ASTptr_operator(int id) {
    super(id);
  }

  public ASTptr_operator(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}