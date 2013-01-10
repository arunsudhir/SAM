package edu.vt.cs.sam.visitors;

import edu.vt.cs.sam.readers.*;
import edu.vt.cs.sam.CodeHandler;
import edu.vt.cs.sam.attributes.JavaClassAttributes;
import edu.vt.cs.sam.attributes.JavaMethodAttributes;

public class CPPParserVisitorAdapter implements CPPParserVisitor {

    protected CodeHandler handler;
    protected boolean startedClass = false;
    public CPPParserVisitorAdapter(CodeHandler handler) {
        this.handler = handler;
    }
    
    /**
     * Get the value of handler
     *
     * @return the value of handler
     */
    public CodeHandler getHandler() {
        return handler;
    }

    /**
     * Set the value of handler
     *
     * @param handler new value of handler
     */
    public void setHandler(CodeHandler handler) {
        this.handler = handler;
    }
    
    public Object visit(SimpleNode node, Object data) {
        return node.childrenAccept(this, data);        
    }

    public Object visit(ASTtranslation_unit node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTexternal_declaration node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTfunction_definition node, Object data) {
        JavaMethodAttributes attrs = new JavaMethodAttributes();
        FunctionDefinitionVisitor visitor = new FunctionDefinitionVisitor(handler);
        for(int i = 0; i < node.jjtGetNumChildren(); ++i) {
            if(!(node.jjtGetChild(i) instanceof ASTfunc_decl_def)) {
                node.jjtGetChild(i).jjtAccept(visitor, attrs);
            }
        }
        handler.startMethod(attrs);
        for(int i = 0; i < node.jjtGetNumChildren(); ++i) {
            if(node.jjtGetChild(i) instanceof ASTfunc_decl_def) {
                node.jjtGetChild(i).jjtAccept(visitor, attrs);
            }
        }
        handler.endMethod(attrs);        
        return attrs;
    }

    public Object visit(ASTfunc_decl_def node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTlinkage_specification node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdeclaration node, Object data) {
        boolean isClass = isClassType(node);
        Object operatingData = data;
        if(!isClass) {
            CPPParser.endLine(node);
        } else {
            TypedefSeekingVisitor seeker = new TypedefSeekingVisitor();
            node.childrenAccept(seeker, data);
            JavaClassAttributes attrs = new JavaClassAttributes();
            operatingData = attrs;
            if(seeker.isTypedef()) {
                TypedefStructVisitor structVisitor = new TypedefStructVisitor(handler);
                node.childrenAccept(structVisitor, attrs);
                CPPParser.endLine(ClassDefinitionVisitor.createClassHeaderLineNode(findClassSpecifier(node)));
                handler.startClass(attrs);
                startedClass = true;                
            } 
        }        
        return node.childrenAccept(this, operatingData);        
    }

    public Object visit(ASTtype_modifiers node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdeclaration_specifiers node, Object data) {   
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTsimple_type_specifier node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTscope_override_lookahead node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTscope_override node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTqualified_id node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTptr_to_member node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTqualified_type node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTtype_qualifier node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTstorage_class_specifier node, Object data) {        
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTbuiltin_type_specifier node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTinit_declarator_list node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTinit_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTclass_head node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTclass_specifier node, Object data) {
        JavaClassAttributes attrs = (data != null && (data instanceof JavaClassAttributes)) ?
            (JavaClassAttributes) data : new JavaClassAttributes();
        if(attrs.getClassName() == null || attrs.getClassName().trim().length() == 0) {
            attrs.setClassName(node.getImage());
        }
        ClassDefinitionVisitor visitor = new ClassDefinitionVisitor(handler);
        collectBaseClasses(node, visitor, attrs);
        if(!startedClass) {
            CPPParser.endLine(ClassDefinitionVisitor.createClassHeaderLineNode(node));
            handler.startClass(attrs);
        }
        visitNonBaseClasses(node, visitor, data);
        handler.endClass(attrs);
        startedClass = false;
        return null;
    }

    public Object visit(ASTbase_clause node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTbase_specifier node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTaccess_specifier node, Object data) {
        CPPParser.endLine(node);
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTmember_declaration node, Object data) {
        if(node.jjtGetNumChildren() < 1 || !
                ((node.jjtGetChild(0) instanceof ASTaccess_specifier) || 
                (node.jjtGetChild(0) instanceof ASTfunction_definition) ||
                (node.jjtGetChild(0) instanceof ASTctor_definition))) {
            CPPParser.endLine(node);
        }
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTmember_declarator_list node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTmember_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTconversion_function_decl_or_def node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTenum_specifier node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTenumerator_list node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTenumerator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTptr_operator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTcv_qualifier_seq node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdeclarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdirect_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdeclarator_suffixes node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTfunction_declarator_lookahead node, Object data) {        
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTfunction_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTfunction_direct_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdtor_ctor_decl_spec node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdtor_definition node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTctor_definition node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTctor_declarator_lookahead node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTctor_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTctor_initializer node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTsuperclass_init node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdtor_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTsimple_dtor_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTparameter_list node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTparameter_declaration_list node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTparameter_declaration node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTinitializer node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTtype_name node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTabstract_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTabstract_declarator_suffix node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTtemplate_head node, Object data) {
        CPPParser.endLine(node);
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTtemplate_parameter_list node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTtemplate_parameter node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTtemplate_id node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTtemplate_argument_list node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTtemplate_argument node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTstatement_list node, Object data) {
        return node.childrenAccept(new StatementVisitor(handler), data);
    }

    public Object visit(ASTstatement node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTlabeled_statement node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTcompound_statement node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTselection_statement node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTiteration_statement node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTjump_statement node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTtry_block node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASThandler node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTexception_declaration node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTthrow_statement node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTexpression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTassignment_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTconditional_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTconstant_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTlogical_or_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTlogical_and_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTinclusive_or_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTexclusive_or_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTand_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTequality_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTrelational_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTshift_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTadditive_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTmultiplicative_expression node, Object data) {
        // short-circuiting this to hopefully speed up the visiting...
        return null;
    }

    public Object visit(ASTpm_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTcast_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTunary_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTnew_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTnew_type_id node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTnew_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdirect_new_declarator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTnew_initializer node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTdelete_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTunary_operator node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTpostfix_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTid_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTprimary_expression node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTexpression_list node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTconstant node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASToptor node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTexception_spec node, Object data) {
        return node.childrenAccept(this, data);
    }

    public Object visit(ASTexception_list node, Object data) {
        return node.childrenAccept(this, data);
    }

    protected void collectBaseClasses(ASTclass_specifier node, ClassDefinitionVisitor visitor, JavaClassAttributes attrs) {
        for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
            if (node.jjtGetChild(i) instanceof ASTbase_clause) {
                visitor.visit((SimpleNode) node.jjtGetChild(i), attrs);
            }
        }
    }

    protected void visitNonBaseClasses(ASTclass_specifier node, ClassDefinitionVisitor visitor, Object data) {
        for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
            if (!(node.jjtGetChild(i) instanceof ASTbase_clause)) {
                node.jjtGetChild(i).jjtAccept(visitor, data);
            }
        }
    }

    public Object visit(ASTelse_statement node, Object data) {
        return node.childrenAccept(this, data);
    }
    
    protected boolean isClassType(SimpleNode node) {
        boolean retVal = false;
        for(int i = 0; !retVal && i < node.jjtGetNumChildren(); ++i) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
            if(child instanceof ASTdeclaration_specifiers) {
                for(int j = 0; !retVal && j < child.jjtGetNumChildren(); ++j) {
                    if(child.jjtGetChild(j) instanceof ASTclass_specifier) {
                        retVal = true;
                    }
                }
            }
        }
        return retVal;
    }

    public Object visit(ASTtemplate_default node, Object data) {
        return node.childrenAccept(this, data);
    }

    private ASTclass_specifier findClassSpecifier(ASTdeclaration node) {
        for(int i = 0; i < node.jjtGetNumChildren(); ++i) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
            if(child instanceof ASTdeclaration_specifiers) {
                for(int j = 0; j < child.jjtGetNumChildren(); ++j) {
                    SimpleNode grandchild = (SimpleNode) child.jjtGetChild(j);
                    if(grandchild instanceof ASTclass_specifier) {
                        return (ASTclass_specifier) grandchild;
                    }
                }
            }
        }
        return null;
    }

    public Object visit(ASTpreprocessor_output node, Object data) {
        CPPParser.endLine(node);
        return null;
    }

    public Object visit(ASTusing_namespace node, Object data) {
        CPPParser.endLine(node);
        return null;
    }

    public Object visit(ASTnamespace_decl node, Object data) {
        SimpleNode newNode = new SimpleNode(node.getNodeId());
        newNode.beginColumn = node.beginColumn;
        newNode.beginLine = node.beginLine;
        if(node.jjtGetNumChildren() > 0) {
            SimpleNode child = (SimpleNode) node.jjtGetChild(0);
            newNode.endLine = child.endLine;
            newNode.endColumn = child.beginColumn-1;
        } else {
            newNode.endLine = node.endLine;
            newNode.endColumn = node.endColumn;
        }
        CPPParser.endLine(newNode);
        return null;
    }
}
