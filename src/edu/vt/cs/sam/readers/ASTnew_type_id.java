/* Generated By:JJTree: Do not edit this line. ASTnew_type_id.java */

package edu.vt.cs.sam.readers;

public class ASTnew_type_id extends SimpleNode {
  public ASTnew_type_id(int id) {
    super(id);
  }

  public ASTnew_type_id(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
