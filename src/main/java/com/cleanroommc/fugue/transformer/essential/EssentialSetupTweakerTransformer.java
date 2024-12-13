package com.cleanroommc.fugue.transformer.essential;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import top.outlands.foundation.IExplicitTransformer;

//Target: [
//      gg.essential.loader.stage0.EssentialSetupTweaker
// ]
public class EssentialSetupTweakerTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var method : classNode.methods) {
            var instructions = method.instructions;
            for (var insnNode : method.instructions) {
                if (insnNode instanceof MethodInsnNode methodInsnNode) {
                    if (
                            methodInsnNode.owner.equals("gg/essential/loader/stage0/EssentialSetupTweaker") &&
                            methodInsnNode.name.equals("addUrlHack") &&
                            methodInsnNode.desc.equals("(Ljava/lang/ClassLoader;Ljava/net/URL;)V")
                    ) {
                        methodInsnNode.owner = "com/cleanroommc/fugur/helper/HookHelper";
                        methodInsnNode.name = "addURL"
                    }
                }
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