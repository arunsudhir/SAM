/* Generated By:JJTree: Do not edit this line. ASTiteration_statement.java */

package edu.vt.cs.sam.readers;

public class ASTiteration_statement extends SimpleNode {
  public ASTiteration_statement(int id) {
    super(id);
  }

  public ASTiteration_statement(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
