package edu.vt.cs.sam.visitors;

import edu.vt.cs.sam.readers.*;
import edu.vt.cs.sam.CodeHandler;
import edu.vt.cs.sam.attributes.JavaMethodAttributes;

public class FunctionDefinitionVisitor extends CPPParserVisitorAdapter {

    protected int beginLine = Integer.MAX_VALUE;
    protected int beginColumn = Integer.MAX_VALUE;
    
    public FunctionDefinitionVisitor(CodeHandler handler) {
        super(handler);
    }
    
    @Override
    public Object visit(ASTstorage_class_specifier node, Object data) {
        findBeginning(node);
        ((JavaMethodAttributes) data).addMethodModifier(node.getImage());
        return null; // This is a terminal, so no need to visit children
    }

    @Override
    public Object visit(ASTqualified_id node, Object data) {
        findBeginning((SimpleNode) node);
        ((JavaMethodAttributes) data).setMethodReturnType(node.getImage());
        return null; // This is a terminal, so no need to visit children
    }

    @Override
    public Object visit(ASTbuiltin_type_specifier node, Object data) {
        findBeginning((SimpleNode) node);
        ((JavaMethodAttributes) data).setMethodReturnType(node.getImage());
        return null;
    }

    @Override
    public Object visit(ASTfunction_declarator node, Object data) {
        findBeginning((SimpleNode) node);
        SimpleNode sn = new SimpleNode(node.getNodeId());
        sn.beginColumn = beginColumn;
        sn.beginLine = beginLine;
        sn.endColumn = node.endColumn;
        sn.endLine = node.endLine;
        CPPParser.endLine(sn);
        return new FunctionDeclarationVisitor(handler).visit(node, data);
    }

    protected void findBeginning(SimpleNode node) {        
        beginLine = Math.min(beginLine, node.beginLine);
        if (beginLine == node.beginLine) {
            beginColumn = Math.min(beginColumn, node.beginColumn);
        }
    }
    
    private class FunctionDeclarationVisitor extends CPPParserVisitorAdapter {

        public FunctionDeclarationVisitor(CodeHandler handler) {
            super(handler);
        }
                
        @Override
        public Object visit(ASTqualified_id node, Object data) {
            ((JavaMethodAttributes) data).setMethodName(node.getImage());
            return null; // This is a terminal, so no need to visit children
        }

        @Override
        public Object visit(ASTparameter_declaration node, Object data) {
            new ParameterDefinitionVisitor(handler).visit(node, data);            
            ((JavaMethodAttributes) data).addParameter(",");            
            return null;
        }

        @Override
        public Object visit(ASTexception_list node, Object data) {
            new ExceptionListVisitor(handler).visit(node, data);
            return null;
        }
        
        private class ExceptionListVisitor extends CPPParserVisitorAdapter {

            public ExceptionListVisitor(CodeHandler handler) {
                super(handler);
            }
                        
            @Override
            public Object visit(ASTqualified_id node, Object data) {
                ((JavaMethodAttributes) data).addException(node.getImage());
                return null;
            }

            @Override
            public Object visit(ASTbuiltin_type_specifier node, Object data) {
                ((JavaMethodAttributes) data).addException(node.getImage());
                return null;
            }
            
        }
    }        
}
