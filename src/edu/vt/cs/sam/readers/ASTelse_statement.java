/* Generated By:JJTree: Do not edit this line. ASTelse_statement.java */

package edu.vt.cs.sam.readers;

public class ASTelse_statement extends SimpleNode {
  public ASTelse_statement(int id) {
    super(id);
  }

  public ASTelse_statement(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
