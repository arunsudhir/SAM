/* Generated By:JJTree: Do not edit this line. ASTdirect_new_declarator.java */

package edu.vt.cs.sam.readers;

public class ASTdirect_new_declarator extends SimpleNode {
  public ASTdirect_new_declarator(int id) {
    super(id);
  }

  public ASTdirect_new_declarator(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
