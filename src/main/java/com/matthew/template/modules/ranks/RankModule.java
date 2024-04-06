package com.matthew.template.modules.ranks;

import com.matthew.template.api.ServerModule;
import com.matthew.template.modules.manager.ServerModuleManager;
import com.matthew.template.modules.permissions.commands.PermissionsCommand;
import com.matthew.template.modules.ranks.command.RankCommand;
import com.matthew.template.modules.ranks.structure.Rank;
import com.matthew.template.modules.ranks.structure.RankType;
import com.matthew.template.modules.storage.DataStorageModule;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RankModule implements ServerModule {

    private final JavaPlugin plugin;

    private final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
    private final DataStorageModule storage = moduleManager.getRegisteredModule(DataStorageModule.class);
    private final Set<Rank> ranks;

    public RankModule(JavaPlugin plugin) {
        this.plugin = plugin;
        this.ranks = new HashSet<>();
        ranks.add(new Rank(RankType.ADMIN, "[ADMIN]", false));
        ranks.add(new Rank(RankType.MEMBER, "[MEMBER]", false));
    }

    public Set<Rank> getRanks() {
        assert storage != null;
        return storage.getRanks();
    }

    @Override
    public void setUp() {
        for(Rank rank: ranks) {
            assert storage != null;
            storage.addRank(rank);
        }
        CommandExecutor rankCommand = new RankCommand();
        Objects.requireNonNull(plugin.getCommand("rank")).setExecutor(rankCommand);
    }

    @Override
    public void teardown() {

    }
}
