package git.ridglef.litematica.printer;

import git.ridglef.litematica.printer.impl.ExampleAddonModule;
import git.ridglef.litematica.printer.modules.LitematicaPrinter;
import com.google.gson.JsonObject;
import dev.boze.api.BozeInstance;
import dev.boze.api.Globals;
import dev.boze.api.addon.Addon;
import dev.boze.api.addon.AddonMetadata;
import dev.boze.api.addon.AddonVersion;
import dev.boze.api.addon.command.AddonDispatcher;
import dev.boze.api.addon.module.AddonModule;
import dev.boze.api.config.Serializable;
import dev.boze.api.exception.AddonInitializationException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;

import java.util.ArrayList;
import java.util.List;

public class ExampleAddon implements ModInitializer, Addon, Serializable<ExampleAddon> {

    public final AddonMetadata metadata = new AddonMetadata(
            "boze-litematica-printer",
            "boze-litematica-printer",
            "boze-litematica-printer",
            new AddonVersion(1, 0, 0));

    private final ArrayList<AddonModule> modules = new ArrayList<>();

    @Override
    public void onInitialize() {
        try {
            BozeInstance.INSTANCE.registerAddon(this);
        } catch (AddonInitializationException e) {
            Log.error(LogCategory.LOG, "Failed to initialize addon: " + getMetadata().id(), e);
        }
    }

    @Override
    public AddonMetadata getMetadata() {
        return metadata;
    }

    @Override
    public boolean initialize() {
        // Register package
        BozeInstance.INSTANCE.registerPackage("com.example.addon");

        // Initialize module
        modules.add(new LitematicaPrinter());

        // Load config
        Globals.getJsonTools().loadObject(this, "config", this);

        return true;
    }

    @Override
    public void shutdown() {
        // Save config
        Globals.getJsonTools().saveObject(this, "config", this);
    }

    @Override
    public List<AddonModule> getModules() {
        return modules;
    }

    @Override
    public AddonDispatcher getDispatcher() {
        return null;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        for (AddonModule module : modules) {
            object.add(module.getInfo().getName(), ((ExampleAddonModule) module).toJson());
        }
        return object;
    }

    @Override
    public ExampleAddon fromJson(JsonObject jsonObject) {
        for (AddonModule module : modules) {
            if (jsonObject.has(module.getInfo().getName())) {
                ((ExampleAddonModule) module).fromJson(jsonObject.getAsJsonObject(module.getInfo().getName()));
            }
        }
        return this;
    }
}
