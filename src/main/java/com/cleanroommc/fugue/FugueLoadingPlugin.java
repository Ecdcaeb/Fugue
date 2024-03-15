package com.cleanroommc.fugue;

import com.cleanroommc.fugue.config.FugueConfig;
import com.cleanroommc.fugue.transformer.*;
import com.cleanroommc.fugue.transformer.universal.*;
import com.cleanroommc.fugue.transformer.logisticpipes.LogisticPipesTransformer;
import com.cleanroommc.fugue.transformer.logisticpipes.LogisticsClassTransformerTransformer;
import com.cleanroommc.fugue.transformer.logisticpipes.LogisticsPipesClassInjectorTransformer;
import com.cleanroommc.fugue.transformer.loliasm.JavaFixesTransformer;
import com.cleanroommc.fugue.transformer.loliasm.LoliReflectorTransformer;
import com.cleanroommc.fugue.transformer.tickcentral.ClassSnifferTransformer;
import com.cleanroommc.fugue.transformer.tickcentral.InitializerTransformer;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import top.outlands.foundation.TransformerDelegate;
import top.outlands.foundation.boot.ActualClassLoader;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@SuppressWarnings("deprecation")
public class FugueLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {

    static {
        ConfigManager.register(FugueConfig.class);
        if (FugueConfig.enableEnderCore) {
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "com.enderio.core.common.transform.EnderCoreTransformer"
                    },
                    new EnderCoreTransformerTransformer()
            );
        }
        if (FugueConfig.enableAR) {
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "zmaster587.advancedRocketry.asm.ClassTransformer"
                    },
                    new ClassTransformerTransformer()
            );
        }
        if (FugueConfig.enableShoulderSurfing) {
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "com.teamderpy.shouldersurfing.asm.transformers.EntityPlayerRayTrace"
                    },
                    new EntityPlayerRayTraceTransformer()
            );
        }
        if (FugueConfig.enableSA){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "pl.asie.splashanimation.core.SplashProgressTransformer"
                    },
                    new SplashProgressTransformerTransformer()
            );
        }
        if (FugueConfig.enableTickCentral){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "com.github.terminatornl.laggoggles.tickcentral.Initializer"
                    },
                    new InitializerTransformer()
            );
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "com.github.terminatornl.tickcentral.api.ClassSniffer",
                    },
                    new ClassSnifferTransformer()
            );
        }
        if (FugueConfig.enableLP){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "logisticspipes.asm.mcmp.ClassBlockMultipartContainerHandler",
                            "logisticspipes.asm.td.ClassRenderDuctItemsHandler"
                    },
                    new LogisticPipesTransformer(1)
            );
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "logisticspipes.asm.td.ClassTravelingItemHandler"
                    },
                    new LogisticPipesTransformer(3)
            );
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "logisticspipes.asm.LogisticsClassTransformer",
                            "logisticspipes.asm.LogisticsPipesClassInjector"
                    },
                    new LogisticsClassTransformerTransformer(ActualClassLoader.class)
            );
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "logisticspipes.asm.LogisticsPipesClassInjector"
                    },
                    new LogisticsPipesClassInjectorTransformer()
            );
        }
        if (FugueConfig.enableOpenAddons){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "pcl.opendisks.OpenDisksUnpack",
                            "pcl.opensecurity.util.SoundUnpack",
                            "pcl.OpenFM.misc.DepLoader"
                    },
                    new OpenDisksUnpackTransformer()
            );
        }
        if (FugueConfig.enableEC){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "austeretony.enchcontrol.common.core.EnumInputClass"
                    },
                    new EnumInputClassTransformer()
            );
        }
        if (FugueConfig.enableTheASM){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "zone.rong.loliasm.LoliReflector"
                    },
                    new LoliReflectorTransformer());
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "zone.rong.loliasm.common.java.JavaFixes"
                    },
                    new JavaFixesTransformer());
        }
        if (FugueConfig.reflectionPatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.reflectionPatchTargets, new ReflectFieldTransformer());
        }
        if (FugueConfig.getURLPatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.getURLPatchTargets, new URLClassLoaderTransformer());
        }
        if (FugueConfig.scriptEngineTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.scriptEngineTargets, new ScriptEngineTransformer());
        }
        if (FugueConfig.UUIDTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.UUIDTargets, new MalformedUUIDTransformer());
        }
        if (FugueConfig.remapTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.remapTargets, new RemapTransformer());
        }
        if (FugueConfig.nonUpdateTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.nonUpdateTargets, new ConnectionBlockingTransformer());
        }
        if (FugueConfig.remapLWTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.remapLWTargets, new RemapLegacyLWTransformer());
        }
        if (FugueConfig.remapReflectionTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.remapReflectionTargets, new RemapSunReflectionTransformer());
        }
        if (!FugueConfig.finalRemovingTargets.isEmpty()) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.finalRemovingTargets.keySet().toArray(new String[0]), new FinalStripperTransformer(FugueConfig.finalRemovingTargets));
        }

    }
    
    @Override
    public String[] getASMTransformerClass() {
        if (FugueConfig.enableTFCMedical) {
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "com.lumintorious.tfcmedicinal.CommonRegistrar$",
                            "com.lumintorious.tfcmedicinal.object.mpestle.MPestleRecipe$",
                            "com.lumintorious.tfcmedicinal.object.heater.HeaterRecipe"
                    },
                    new CommonRegistrar$Transformer()
            );
        }
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> map) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public List<String> getMixinConfigs() {
        return Collections.emptyList();
    }
}