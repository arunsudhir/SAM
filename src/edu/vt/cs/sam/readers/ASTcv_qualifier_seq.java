/* Generated By:JJTree: Do not edit this line. ASTcv_qualifier_seq.java */

package edu.vt.cs.sam.readers;

public class ASTcv_qualifier_seq extends SimpleNode {
  public ASTcv_qualifier_seq(int id) {
    super(id);
  }

  public ASTcv_qualifier_seq(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
