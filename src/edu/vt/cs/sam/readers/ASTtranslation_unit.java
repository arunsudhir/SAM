/* Generated By:JJTree: Do not edit this line. ASTtranslation_unit.java */

package edu.vt.cs.sam.readers;

public class ASTtranslation_unit extends SimpleNode {
  public ASTtranslation_unit(int id) {
    super(id);
  }

  public ASTtranslation_unit(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
