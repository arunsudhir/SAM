/* Generated By:JJTree: Do not edit this line. ASTclass_head.java */

package edu.vt.cs.sam.readers;

public class ASTclass_head extends SimpleNode {
  public ASTclass_head(int id) {
    super(id);
  }

  public ASTclass_head(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
