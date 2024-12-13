package com.cleanroommc.fugue.transformer.essential;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

//Target: [
//      gg.essential.loader.stage0.EssentialSetupTweaker
// ]
public class EssentialSetupTweakerTransformer implements IExplicitTransformer, Opcodes {

    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var method : classNode.methods) {
            if ("addUrlHack".equals(method.name)) {
                method.instructions.clear();
                method.visitVarInsn(ALOAD, 0);
                method.visitVarInsn(ALOAD, 1);
                method.visitMethodInsn(INVOKESTATIC, "com/cleanroommc/fugur/helper/HookHelper", "addURL", "(Ljava/lang/ClassLoader;Ljava/net/URL;)V", false);
                method.visitInsn(RETURN);
            }
        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    @Override
    public int getPriority() {
        return 0;
    }
}