package edu.vt.cs.sam.visitors;

import edu.vt.cs.sam.readers.*;
import edu.vt.cs.sam.CodeHandler;
import edu.vt.cs.sam.attributes.JavaClassAttributes;
import edu.vt.cs.sam.attributes.JavaMethodAttributes;

public class ClassDefinitionVisitor extends CPPParserVisitorAdapter {

    private String accessModifier = "";
    private String className = "";

    public ClassDefinitionVisitor(CodeHandler handler) {
        super(handler);
    }

    @Override
    public Object visit(ASTbase_specifier node, Object data) {
        BaseSpecifierVisitor visitor = new BaseSpecifierVisitor(handler);
        node.jjtAccept(visitor, data);
        node.childrenAccept(visitor, data);
        ((JavaClassAttributes) data).addExtends(visitor.toString());
        return null;
    }

    @Override
    public Object visit(ASTaccess_specifier node, Object data) {
        accessModifier = node.getImage();
        return null;
    }

    @Override
    public Object visit(ASTclass_specifier node, Object data) {
        className = node.getImage();
        ((JavaClassAttributes) data).setClassName(className);
        CPPParser.endLine(createClassHeaderLineNode(node));
        return super.visit(node, data);
    }
        

    @Override
    public Object visit(ASTdtor_definition node, Object data) {
        JavaMethodAttributes attrs = new JavaMethodAttributes();
        attrs.addMethodModifier(accessModifier);
        attrs.setMethodName("~" + findClassNameFromParents(node));
        new ParameterDefinitionVisitor(handler).visit(node, attrs);
        handler.startMethod(attrs);
        for(int i = 0; i < node.jjtGetNumChildren(); ++i) {
            ((SimpleNode) node.jjtGetChild(i)).childrenAccept(this, data);
        }
        handler.endMethod(attrs);
        return attrs;
    }

    @Override
    public Object visit(ASTctor_definition node, Object data) {
        JavaMethodAttributes attrs = new JavaMethodAttributes();
        attrs.addMethodModifier(accessModifier);
        attrs.setConstructor(true);        
        attrs.setMethodName(findClassNameFromParents(node));
        for(int i = 0; i < node.jjtGetNumChildren(); ++i) {
            if(node.jjtGetChild(i) instanceof ASTctor_declarator) {
                SimpleNode child = (SimpleNode) node.jjtGetChild(i);
                for(int j = 0; j < child.jjtGetNumChildren(); ++j) {
                    if(child.jjtGetChild(j) instanceof ASTparameter_list) {
                        new ParameterDefinitionVisitor(handler).visit(node, attrs);
                        break;
                    }
                }
                break;
            }
        }
        handler.startMethod(attrs);
        for(int i = 0; i < node.jjtGetNumChildren(); ++i) {
            ((SimpleNode) node.jjtGetChild(i)).childrenAccept(this, data);
        }
        handler.endMethod(attrs);
        return attrs;
    }

    protected String findClassNameFromParents(SimpleNode node) {
        String retVal = className;
        if (retVal == null || retVal.trim().length() == 0) {
            SimpleNode parent = (SimpleNode) node.jjtGetParent();
            while (!(parent instanceof ASTclass_specifier)) {
                parent = (SimpleNode) parent.jjtGetParent();
            }
            retVal = parent.getImage();
            retVal = retVal.trim();
            int lastSpace = retVal.lastIndexOf(" ");
            if(lastSpace >= 0) {
                retVal = retVal.substring(lastSpace + 1);
            }
        }
        return retVal;
    }
    
    static SimpleNode createClassHeaderLineNode(ASTclass_specifier node) {
        SimpleNode retVal = new SimpleNode(node.getNodeId());
        retVal.beginColumn = node.beginColumn;
        retVal.beginLine = node.beginLine;
        retVal.endColumn = node.endColumn;
        retVal.endLine = node.endLine;
        for(int i = 0; i < node.jjtGetNumChildren(); ++i) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
            if(child instanceof ASTbase_clause) {
                retVal.endColumn = child.endColumn;
                retVal.endLine = child.endLine;
                break;
            } else if(child instanceof ASTmember_declaration) {
                if(child.beginColumn == 1) {
                    retVal.endLine = child.beginLine - 1;
                    /* the endLine method checks against line length, so MAX_VALUE
                     * is effectively a sentinel
                     */
                    retVal.endColumn = Integer.MAX_VALUE;
                } else {
                    retVal.endLine = child.beginLine;
                    retVal.endColumn = child.beginColumn - 1;
                }
                break;
            }
        }
        return retVal;
    }
    private class BaseSpecifierVisitor extends CPPParserVisitorAdapter {
        public String visibility = "";
        public String className = "";

        public BaseSpecifierVisitor(CodeHandler handler) {
            super(handler);
        }
        
        @Override
        public Object visit(ASTbase_specifier node, Object data) {
            this.className = node.getImage();
            return null;
        }

        @Override
        public Object visit(ASTaccess_specifier node, Object data) {
            this.visibility = node.getImage();
            return null;
        }

        @Override
        public String toString() {
            return visibility + " " + className;
        }
    }
}
