/* Generated By:JJTree: Do not edit this line. ASTtype_qualifier.java */

package edu.vt.cs.sam.readers;

public class ASTtype_qualifier extends SimpleNode {
  public ASTtype_qualifier(int id) {
    super(id);
  }

  public ASTtype_qualifier(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
