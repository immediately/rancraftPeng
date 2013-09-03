package rancraftPenguins;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
//import net.minecraft.block.BlockCloth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityPenguin extends net.minecraft.entity.passive.EntityTameable
{
    protected boolean looksWithInterest;
    protected float field_25048_b;
    protected float field_25054_c;

    /** true is the penguin is wet else false */
    protected boolean isShaking;
    protected boolean field_25052_g;
    
    protected int GenericShearing = 1;
    
    //protected String texturePath = "/assets/rancraftpenguins/textures/models/";
    
    /**
     * This time increases while penguin is shaking and emitting water particles.
     */
    protected float timePenguinIsShaking;
    protected float prevTimePenguinIsShaking;
	protected int timeDry = 0;  // how long since the penguin has left the water

    public EntityPenguin(World par1World)
    {
        super(par1World);
        looksWithInterest = false;
        this.getNavigator().setAvoidsWater(true); // change this later?
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }

    /**
     * Returns true if penguins can be tamed with stuffID
     */
    protected boolean isPenguinFood(int stuffID)
    {
        return (stuffID == net.minecraft.item.Item.fishRaw.itemID
        		|| stuffID == 23272 /* aquaculture mod raw fish fillet and whale steak */
        		|| stuffID == 23273);
    }

    /* overrides the fn for EntityLiving */
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    /**
     * Checks if the parameter is an wheat item.
     * The one in EntityAnimal sometimes causes a crash because it tries to evaluate without checking for null first
     */
    public boolean isWheat(ItemStack par1ItemStack)
    {
        if (par1ItemStack == null)
        {
            return false;
        } else {
            return (par1ItemStack.itemID == net.minecraft.item.Item.wheat.itemID);
        }
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
    {
        super.setAttackTarget(par1EntityLivingBase);

        if (par1EntityLivingBase == null)
        {
            if (!this.isAngry())
            {
                return;
            }

            this.setAngry(false);
            List list = this.worldObj.getEntitiesWithinAABB(this.getClass(), AxisAlignedBB.getAABBPool().getAABB(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(16.0D, 10.0D, 16.0D));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityPenguin entitypenguin = (EntityPenguin)iterator.next();

                if (this != entitypenguin)
                {
                    entitypenguin.setAngry(false);
                }
            }
        }
        else
        {
            this.setAngry(true);
        }
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
        //dataWatcher.updateObject(18, Integer.valueOf(getHealth()));
        dataWatcher.updateObject(18, Integer.valueOf(getMaxHealth()));
    }

    abstract public int getMaxHealth();

    protected void entityInit()
    {
        super.entityInit();
        //dataWatcher.addObject(18, new Integer(getHealth()));
        dataWatcher.addObject(18, new Integer(getMaxHealth()));
        this.dataWatcher.addObject(19, new Byte((byte)0));
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * returns the directory and filename as a String
     */
    /*public String getTexture()
    {
        //return super.getTexture();
        return super.func_110581_b(textureLocation).func_110552_b();
    }*/

    /**
     * Sets the size of this mob (useful for adjusting penguin's dimensions when it swims)
     */
    protected void setSize2(float width, float height)
    {
    	super.setSize(width, height);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Angry", isAngry());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        setAngry(par1NBTTagCompound.getBoolean("Angry"));
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return isAngry();
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    abstract protected String getLivingSound();

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    abstract protected String getHurtSound();

    /**
     * Returns the sound this mob makes on death.
     */
    abstract protected String getDeathSound();

    /**
     * Returns the volume for the sounds this mob makes.
     */
    abstract protected float getSoundVolume();

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    abstract protected int getDropItemId();

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!worldObj.isRemote && isShaking && !field_25052_g && !hasPath() && onGround)
        {
            field_25052_g = true;
            timePenguinIsShaking = 0.0F;
            prevTimePenguinIsShaking = 0.0F;
            worldObj.setEntityState(this, (byte)8);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        field_25054_c = field_25048_b;

        if (looksWithInterest)
        {
            field_25048_b = field_25048_b + (1.0F - field_25048_b) * 0.4F;
        }
        else
        {
            field_25048_b = field_25048_b + (0.0F - field_25048_b) * 0.4F;
        }

        if (looksWithInterest)
        {
            numTicksToChaseTarget = 10;
        }

        if (isWet())
        {
            isShaking = true;
            field_25052_g = false;
            timePenguinIsShaking = 0.0F;
            prevTimePenguinIsShaking = 0.0F;
        }
        else if ((isShaking || field_25052_g) && field_25052_g)
        {
            if (timePenguinIsShaking == 0.0F)
            {
                worldObj.playSoundAtEntity(this, "mob.wolf.shake", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }

            prevTimePenguinIsShaking = timePenguinIsShaking;
            timePenguinIsShaking += 0.05F;

            if (prevTimePenguinIsShaking >= 2.0F)
            {
                isShaking = false;
                field_25052_g = false;
                prevTimePenguinIsShaking = 0.0F;
                timePenguinIsShaking = 0.0F;
            }

            if (timePenguinIsShaking > 0.4F)
            {
                float f = (float)boundingBox.minY;
                int i = (int)(MathHelper.sin((timePenguinIsShaking - 0.4F) * (float)Math.PI) * 7F);

                for (int j = 0; j < i; j++)
                {
                    float f1 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
                    float f2 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
                    worldObj.spawnParticle("splash", posX + (double)f1, f + 0.8F, posZ + (double)f2, motionX, motionY, motionZ);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean getPenguinShaking()
    {
        return this.isShaking;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Used when calculating the amount of shading to apply while the penguin is shaking.
     */
    public float getShadingWhileShaking(float par1)
    {
        return 0.75F + ((prevTimePenguinIsShaking + (timePenguinIsShaking - prevTimePenguinIsShaking) * par1) / 2.0F) * 0.25F;
    }

    @SideOnly(Side.CLIENT)
    public float getShakeAngle(float par1, float par2)
    {
        float f = (prevTimePenguinIsShaking + (timePenguinIsShaking - prevTimePenguinIsShaking) * par1 + par2) / 1.8F;

        if (f < 0.0F)
        {
            f = 0.0F;
        }
        else if (f > 1.0F)
        {
            f = 1.0F;
        }

        return MathHelper.sin(f * (float)Math.PI) * MathHelper.sin(f * (float)Math.PI * 11F) * 0.15F * (float)Math.PI;
    }

    @SideOnly(Side.CLIENT)
    public float getInterestedAngle(float par1)
    {
        return (field_25054_c + (field_25048_b - field_25054_c) * par1) * 0.15F * (float)Math.PI;
    }

    public float getEyeHeight()
    {
        return height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves AND penguins.
     */
    public int getVerticalFaceSpeed()
    {
        if (isSitting())
        {
            return 20;
        }
        else
        {
            return super.getVerticalFaceSpeed();
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        Entity entity = par1DamageSource.getEntity();
        aiSit.setSitting(false);

        if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
        {
            par2 = (par2 + 1) / 2;
        }

        return super.attackEntityFrom(par1DamageSource, par2);
    }

    /**
     * Called when this class wants to know what to drop on shearing
     */
    public Item droppedFeather()
    {
    	Item dropped;
    	dropped = Item.potato;
    	return dropped;
    }
    
    /**
     * Called when a penguin gets sheared
     */
    public boolean interactShearing(EntityPlayer par1EntityPlayer)
    {
    	if (!worldObj.isRemote)
        {
	        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

	        //checks if there is something in the player's inventory to prevent crash
	        //checks if it is supposed to run this shearing code or not
	        if(var2 != null){
	        
		        if (!isChild() && ((var2.itemID == Item.shears.itemID) || (var2.itemID == RanCraftPenguins.PenguinShears.itemID))) // can't shear chicks
		        {
		        	// shear
		        	if(!isTamed())
		        	{
		        		setAngry(true);
	        			setAttackTarget(par1EntityPlayer);
		        	} else {
		        		int shearDurabilityInverse;
		        		int angerChanceInverse;
		        		
				        if (var2.itemID == RanCraftPenguins.PenguinShears.itemID){
				        	angerChanceInverse = 5; // 20% chance penguin may go wild and attack
				        	shearDurabilityInverse = 3; // 33% chance shears won't get worn
				        } else { // ordinary shears
				        	angerChanceInverse = 3; // 33% chance penguin may go wild and attack
				        	shearDurabilityInverse = 1; // 0% chance shears won't get worn
				        }

				        int i = rand.nextInt(angerChanceInverse);
		        		if(i == 0){ // 33% chance for normal; 20% chance for penguin shears
		                    aiSit.setSitting(false); // stand up
		        			setTamed(false);
		        			setAngry(true);
		        			setAttackTarget(par1EntityPlayer);
		        		} else {
		        			int k = rand.nextInt(3); // 0-2 drops
		        			if(k > 0){
				                this.worldObj.playSoundAtEntity(this, "mob.sheep.shear", 1.0F, 1.0F);
		        			}
		        			for(int j = 0; j < k; j++) {
		        				Item dropped;
		        				dropped = this.droppedFeather();
		        				EntityItem entityitem = entityDropItem(new ItemStack(dropped), 1.0F); 
			                    entityitem.motionY += rand.nextFloat() * 0.05F;
			                    entityitem.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
			                    entityitem.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
		            		}
		        		}
		        		int k = rand.nextInt(shearDurabilityInverse);
		        		if (k < 3){
		        			var2.damageItem(1, par1EntityPlayer); // 2/3 of the time if variable is 3; always if variable is 1
		        		}
		        	}
		        }
		        // holding fish or other penguin food
		        else if (isPenguinFood(var2.itemID))
		        {
		        	// penguin is not tame (could be angry) so try to tame it
		        	if(!isTamed())
		        	{
	                    if (!par1EntityPlayer.capabilities.isCreativeMode) 
	                    {
	                    	var2.stackSize--;
		        		}
		
		                if (var2.stackSize <= 0)
		                {
		                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
		                }
		                if (rand.nextInt(3) == 0)
		                {
		                    setAngry(false);
		                    setTamed(true);
		                    setPathToEntity(null);
		                    setAttackTarget(null);
		                    this.aiSit.setSitting(true);
		                    setEntityHealth(20);
		                    setOwner(par1EntityPlayer.username);
		                    this.playTameEffect(true);
		                    this.worldObj.setEntityState(this, (byte)7);
		                }
		                else
		                {
		                    this.playTameEffect(false);
		                    worldObj.setEntityState(this, (byte)6);
		                }
		                return true;
		        	} else { // penguin is tame (and may be a child). so feed it if it's hungry
		                ItemFood var3 = (ItemFood)Item.itemsList[var2.itemID];
	
		                if (this.dataWatcher.getWatchableObjectInt(18) < 20)
		                {
		                    this.heal(var3.getHealAmount());
		                    if (!par1EntityPlayer.capabilities.isCreativeMode) 
		                    {
		                    	var2.stackSize--;
			        		}
		                    return true;
		                }
		        	}
		        }
	        }
	        
	        // sit toggle
	        if (par1EntityPlayer.username.equalsIgnoreCase(getOwnerName()) &&
            	(var2 == null || (!isPenguinFood(var2.itemID) && !isWheat(var2) && (var2.itemID != Item.shears.itemID) && 
            	(var2.itemID != RanCraftPenguins.PenguinShears.itemID))))
	        {
                this.aiSit.setSitting(!this.isSitting());
	            isJumping = false;
	            setPathToEntity(null);
	        }
        }
    	return false; //default value does nothing
    }
    
    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        boolean shearingValue = this.interactShearing(par1EntityPlayer);
        if (shearingValue)
        {
        	return true;
        }
        return super.interact(par1EntityPlayer);
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 8)
        {
            field_25052_g = true;
            timePenguinIsShaking = 0.0F;
            prevTimePenguinIsShaking = 0.0F;
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    abstract public int getMaxSpawnedInChunk();

    /**
     * gets this penguin's angry state
     */
    public boolean isAngry()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    /**
     * sets this penguin's angry state to true if the boolean argument is true
     */
    public void setAngry(boolean par1)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 2)));
        }
        else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -3)));
        }
    }

    /**
     * [This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.]
     */
    abstract public EntityAnimal spawnBabyAnimal(EntityAgeable par1EntityAgeable);

    public void func_48150_h(boolean par1)
    {
        looksWithInterest = par1;
    }

    abstract public boolean canMateWith(EntityAnimal par1EntityAnimal);

    @Override
    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }
}