/* Generated By:JJTree: Do not edit this line. ASTparameter_list.java */

package edu.vt.cs.sam.readers;

public class ASTparameter_list extends SimpleNode {
  public ASTparameter_list(int id) {
    super(id);
  }

  public ASTparameter_list(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
