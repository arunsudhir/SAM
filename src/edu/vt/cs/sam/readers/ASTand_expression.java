/* Generated By:JJTree: Do not edit this line. ASTand_expression.java */

package edu.vt.cs.sam.readers;

public class ASTand_expression extends SimpleNode {
  public ASTand_expression(int id) {
    super(id);
  }

  public ASTand_expression(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}