/* Generated By:JJTree: Do not edit this line. ASTexception_spec.java */

package edu.vt.cs.sam.readers;

public class ASTexception_spec extends SimpleNode {
  public ASTexception_spec(int id) {
    super(id);
  }

  public ASTexception_spec(CPPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CPPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}