package rancraftPenguins;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EntityAISwimmingPeng extends EntityAIBase
{
    private EntityLiving theEntity;
    private double xTarget;
    private double yTarget;
    private double zTarget;
    private float speed;

    public EntityAISwimmingPeng(EntityLiving par1EntityLiving, float moveSpeed)
    {
        this.theEntity = par1EntityLiving;
        this.setMutexBits(4);
        par1EntityLiving.getNavigator().setCanSwim(true);
        speed = moveSpeed;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
    	boolean retVal = false;
    	
		if(!this.theEntity.isInWater()){ // not in the water -- check how long
	    	if(((EntityPenguin)this.theEntity).timeDry > 16 ){ // not just a bounce
	    		((EntityPenguin)this.theEntity).timeDry = 0;
	    		this.theEntity.getNavigator().clearPathEntity();
	
	    		//if(this.xTarget > 0.0F){
	    			this.theEntity.getNavigator().tryMoveToXYZ(this.xTarget, this.yTarget, this.zTarget, this.speed); // slow down out of water
	    		//}
	    	} else {
	   			((EntityPenguin)this.theEntity).timeDry++;
	    	}
		}
        if(this.theEntity.isInWater()){
    		((EntityPenguin)this.theEntity).setSize2(this.theEntity.height, this.theEntity.width); // width, height (Switched for penguin not swimming)
        	retVal = true;
        }
        return retVal;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        // get a new target to move to if there's none
        if (this.theEntity.getNavigator().noPath()) {
            Vec3 vec3 = RandomPositionGenerator.findRandomTarget((EntityCreature)this.theEntity, 44, 2);

            if (vec3 != null)
            {
        		//System.out.printf("  Found a path: To %f, %f, %f\n", this.xTarget, this.yTarget, this.zTarget);
                this.xTarget = vec3.xCoord;
                this.yTarget = vec3.yCoord;
                this.zTarget = vec3.zCoord;
    			this.theEntity.getNavigator().tryMoveToXYZ(this.xTarget, this.yTarget, this.zTarget, 2.0F * this.speed);
            } else {
        		//System.out.printf("  Failed to find a path!\n");
            }
        } else {
    		//System.out.printf("  Already got a path: To %f, %f, %f\n", this.xTarget, this.yTarget, this.zTarget);
        }
    }

    /**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.theEntity.getNavigator().tryMoveToXYZ(this.xTarget, this.yTarget, this.zTarget, 1.5F * this.speed);
		((EntityPenguin)this.theEntity).setSize2(this.theEntity.height, this.theEntity.width); // width, height (Switched for penguin swimming)
	}
}
