/* Generated By:JJTree: Do not edit this line. ASTnew_initializer.java */

package edu.vt.cs.sam.readers;

public class ASTnew_initializer extends SimpleNode {
  public ASTnew_initializer(int id) {
    super(id);
  }

  public ASTnew_initializer(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}