/* Generated By:JJTree: Do not edit this line. ASTaccess_specifier.java */

package edu.vt.cs.sam.readers;

public class ASTaccess_specifier extends SimpleNode {
  public ASTaccess_specifier(int id) {
    super(id);
  }

  public ASTaccess_specifier(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
