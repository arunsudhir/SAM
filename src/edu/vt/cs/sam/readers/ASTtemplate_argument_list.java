/* Generated By:JJTree: Do not edit this line. ASTtemplate_argument_list.java */

package edu.vt.cs.sam.readers;

public class ASTtemplate_argument_list extends SimpleNode {
  public ASTtemplate_argument_list(int id) {
    super(id);
  }

  public ASTtemplate_argument_list(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
