/* Generated By:JJTree: Do not edit this line. ASTlogical_and_expression.java */

package edu.vt.cs.sam.readers;

public class ASTlogical_and_expression extends SimpleNode {
  public ASTlogical_and_expression(int id) {
    super(id);
  }

  public ASTlogical_and_expression(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
