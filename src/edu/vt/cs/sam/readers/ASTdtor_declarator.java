/* Generated By:JJTree: Do not edit this line. ASTdtor_declarator.java */

package edu.vt.cs.sam.readers;

public class ASTdtor_declarator extends SimpleNode {
  public ASTdtor_declarator(int id) {
    super(id);
  }

  public ASTdtor_declarator(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}