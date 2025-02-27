package com.cleanroommc.fugue.transformer.tfcmedical;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import top.outlands.foundation.IExplicitTransformer;

public class CommonRegistrar$Transformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {


        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        classNode.version = Opcodes.V21;
        if (classNode.fields != null) 
        {
            for (FieldNode fieldNode : classNode.fields)
            {
                if (fieldNode.name.equals("MODULE$"))
                {
                    fieldNode.access -= Opcodes.ACC_FINAL;
                }
            }
        }
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("registerBarrelRecipes") || methodNode.name.equals("<init>")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.INVOKESTATIC && insnNode instanceof MethodInsnNode methodInsnNode)
                            {
                                if (methodInsnNode.owner.equals("net/dries007/tfc/objects/inventory/ingredient/IIngredient") && (methodInsnNode.name.equals("of") || methodInsnNode.name.equals("empty")))
                                {
                                    methodInsnNode.itf = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
