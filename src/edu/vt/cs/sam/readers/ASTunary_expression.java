/* Generated By:JJTree: Do not edit this line. ASTunary_expression.java */

package edu.vt.cs.sam.readers;

public class ASTunary_expression extends SimpleNode {
  public ASTunary_expression(int id) {
    super(id);
  }

  public ASTunary_expression(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}