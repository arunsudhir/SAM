/* Generated By:JJTree: Do not edit this line. ASTlogical_or_expression.java */

package edu.vt.cs.sam.readers;

public class ASTlogical_or_expression extends SimpleNode {
  public ASTlogical_or_expression(int id) {
    super(id);
  }

  public ASTlogical_or_expression(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
