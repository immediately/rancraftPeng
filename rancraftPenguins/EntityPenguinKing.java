package rancraftPenguins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityPenguinKing extends EntityPenguin
{
    final int MAXHEALTHWILD = 8;
    final int MAXHEALTHTAME = 20;
    /* automatically compute the four terciles */
    int healthTercile1 = (int)((float)MAXHEALTHTAME * 0.25) + 1;
    int healthTercile2 = (int)((float)MAXHEALTHTAME * 0.50) + 1;
    int healthTercile3 = (int)((float)MAXHEALTHTAME * 0.75) + 1;
	private float moveSpeed;

    public EntityPenguinKing(World par1World)
    {
        super(par1World);
        //texture = texturePath + "pengKing.png";
        setSize(0.5F, 1.4F); // width, height?
        moveSpeed = 0.28F; // (was 0.2);
        //looksWithInterest = false;
        //tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(2, new EntityAISwimmingPeng(this, getMoveSpeed()));
        tasks.addTask(2, aiSit);
        tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        tasks.addTask(4, new EntityAIAttackOnCollide(this, moveSpeed, true));
        tasks.addTask(5, new EntityAIFollowOwner(this, moveSpeed, 10F, 2.0F));
        tasks.addTask(6, new EntityAIMate(this, moveSpeed));
        tasks.addTask(7, new EntityAIWander(this, moveSpeed));
        tasks.addTask(9, new EntityAIWatchClosest(this, net.minecraft.entity.player.EntityPlayer.class, 8F));
        tasks.addTask(9, new EntityAILookIdle(this));
        targetTasks.addTask(3, new EntityAIHurtByTargetCustom(this, true));
    }

    /**
     * returns the directory and filename as a String
     */
    /*public String getTexture()
    {
        if (isChild()) {
        	if (isTamed())
                return texturePath + "pengKingCh_tame.png";
            else
            	return texturePath + "pengKingCh.png";
    	}
        if (isAngry()) {
           	return texturePath + "pengKing_angry.png";
        } 
       	if (isTamed()) {
            return texturePath + "pengKing_tame.png";
    	}
        return super.getTexture();
	}*/

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (isAngry())
        {
            return RanCraftPenguins.modID+":penguinKing.angry";
        }

        if (rand.nextInt(RanCraftPenguins.pengQuietInt) == 0) {
	        if(dataWatcher.getWatchableObjectInt(18) < healthTercile1) {
	    		return RanCraftPenguins.modID+":penguinKing.healthd";
	    	} else if(dataWatcher.getWatchableObjectInt(18) < healthTercile2) {
	    		return RanCraftPenguins.modID+":penguinKing.healthc";
	    	} else if(dataWatcher.getWatchableObjectInt(18) < healthTercile3) {
	    		return RanCraftPenguins.modID+":penguinKing.healthb";
	    	} else {
	    		return RanCraftPenguins.modID+":penguinKing.healtha";
	    	}
        } else {
        	return null;
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return RanCraftPenguins.modID+":penguinKing.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return RanCraftPenguins.modID+":penguinKing.death";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    public int getMaxHealth()
    {
        return isTamed() ? MAXHEALTHTAME : MAXHEALTHWILD;
    }

    /**
     * Overrides the version many levels up (in EntityLiving)
     * Because we never want to drop 2 crowns
     * Drops 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int i = getDropItemId();

        if (i == RanCraftPenguins.PenguinCrown.itemID)
        {
            dropItem(i, 1);
        }
        if(i == RanCraftPenguins.PengSkinBlack.itemID)
        {
            int j = rand.nextInt(3);

            if (par2 > 0)
            {
                j += rand.nextInt(par2 + 1);
            }

            for (int k = 0; k < j; k++)
            {
                dropItem(i, 1);
            }
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
		int i = rand.nextInt(10); // 10% vs 90% drops
		int dropped;
		if(i < 1){ // 10% crown, 90% black penguin skin
			dropped = RanCraftPenguins.PenguinCrown.itemID;
		} else {
			dropped = RanCraftPenguins.PengSkinBlack.itemID;
		}
		return dropped;
    }

    public boolean attackEntityAsMob(Entity par1Entity)
    {
        byte byte0 = ((byte)(isTamed() ? 5 : 3));
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), byte0);
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
    	int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        return worldObj.getFullBlockLightValue(i, j, k) > 8 && super.getCanSpawnHere();
    }

    public Item droppedFeather()
    {
    	Item dropped;
    	int i = rand.nextInt(3);
		if(i < 1){ // 33% white, 66% black
			dropped = RanCraftPenguins.PengFeatherWhite;
		} else {
			dropped = RanCraftPenguins.PengFeatherBlack;
		}
		return dropped;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 4;
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityPenguinKing spawnBabyAnimal(EntityAgeable par1EntityAgeable)
    {
        EntityPenguinKing var2 = new EntityPenguinKing(this.worldObj);
        String var3 = this.getOwnerName();

        if (var3 != null && var3.trim().length() > 0)
        {
            var2.setOwner(var3);
            var2.setTamed(true);
        }

        return var2;
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
	@Override
    public boolean canMateWith(EntityAnimal par1EntityAnimal)
    {
        if (par1EntityAnimal == this)
        {
            return false;
        }
        else if (!this.isTamed() || this.isAngry())
        {
            return false;
        }
        else if (!(par1EntityAnimal instanceof EntityPenguinKing))
        {
            return false;
        }
        else
        {
            EntityPenguinKing var2 = (EntityPenguinKing)par1EntityAnimal;
            return !var2.isTamed() ? false : ((this.isSitting() || var2.isSitting()) ? false : this.isInLove() && var2.isInLove());
        }
    }

	public float getMoveSpeed() {
		return moveSpeed;
	}
}
