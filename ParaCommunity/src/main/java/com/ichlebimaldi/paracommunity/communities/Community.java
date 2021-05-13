package com.ichlebimaldi.paracommunity.communities;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import com.ichlebimaldi.paracommunity.claim.Area;
import com.ichlebimaldi.paracommunity.sql.SQLGetter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Community {
    protected String name;
    protected Area area;
    protected List<Player> members;

    protected ParaCommunity plugin;
    protected SQLGetter data = ParaCommunity.data;

    public Community(String name) {
        this.name = name;
    }

    public void retrieveInfo(){
        members = data.getCommunityMembers(this);
        area = new Area();
        area = data.getArea(this);
    }

    public String getName(){
        return name;
    }

    public Area getArea(){
        return area;
    }

    public List getMembers(){
        return members;
    }
}
