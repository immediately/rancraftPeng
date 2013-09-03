package rancraftPenguins.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import rancraftPenguins.EntityPenguinLB;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            ModelBase, ModelRenderer, EntityPenguinLB, MathHelper, 
//            Entity, EntityLiving

public class ModelPenguinLB extends ModelBase
{
	private ModelRenderer headmain;
	private ModelRenderer body;
	private ModelRenderer rightwing;
	private ModelRenderer leftwing;
	private ModelRenderer rightfoot;
	private ModelRenderer leftfoot;
	private ModelRenderer beak;

	private float pi = 3.141593F;

	private float bodyY, headX, headY, headZ, wingY, footY, bodyLean;
	private float headUpOffset; // for swimming

	public ModelPenguinLB()
    {
    	textureWidth = 28;
        textureHeight = 26;
        headX = 0.0F;
        headZ = -1.0F;
        bodyY = 19.0F;
        headY = 16.0F - bodyY;
        footY = 22.0F - bodyY;
        wingY = 19.0F - bodyY;
        bodyLean = pi / 12;

    	headmain = new ModelRenderer(this, 0, 0);
    	/* side,vert (neg is up),forward (neg is forward) Second set is dimensions! */ 
        headmain.addBox(-2F, -3F, -1F, 4, 3, 3);
        headmain.setRotationPoint(headX, headY, headZ);
        beak = new ModelRenderer(this, 15, 0);
        beak.addBox(-1F, -1F, -3F, 2, 1, 2);
        headmain.addChild(beak);
        body = new ModelRenderer(this, 0, 7);
        body.addBox(-2F, -3F, -2F, 4, 6, 3);
        body.setRotationPoint(0F, bodyY, 1F);
        body.addChild(headmain);
        leftwing = new ModelRenderer(this, 0, 17);
        leftwing.addBox(-1F, -1F, -1F, 1, 4, 2);
        leftwing.setRotationPoint(3F, wingY, -1F);
        body.addChild(leftwing);
        rightwing = new ModelRenderer(this, 7, 17);
        rightwing.addBox(0F, -1F, -1F, 1, 4, 2);
        rightwing.setRotationPoint(-3F, wingY, -1F);
        body.addChild(rightwing);
        leftfoot = new ModelRenderer(this, 16, 12);
        leftfoot.addBox(-1F, 0F, -2F, 2, 1, 3);
        leftfoot.setRotationPoint(1F, footY, -2F);
        body.addChild(leftfoot);
        rightfoot = new ModelRenderer(this, 16, 12);
        rightfoot.addBox(-1F, 0F, -2F, 2, 1, 3);
        rightfoot.setRotationPoint(-1F, footY, -2F);
        body.addChild(rightfoot);
    }

    //public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (isChild)
        {
            float f = 2.0F;
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f, 1.0F / f, 1.0F / f);
            GL11.glTranslatef(0.0F, 24F * par7, 0.0F);
            body.render(par7);
            GL11.glPopMatrix();
        }
        else
        {
        	body.render(par7);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
    }

    public void setLivingAnimations(EntityLivingBase entityliving, float f, float f1, float f2)
    {
    	int sittingOffset;
    	float cosf;
    	float cosHalff;
    	float speedSum;
    	float toePointOffset; // for swimming
    	
        EntityPenguinLB entitypenguin = (EntityPenguinLB)entityliving;
    	cosf = MathHelper.cos(f);
    	cosHalff = MathHelper.cos(f/2);
    	speedSum = entitypenguin.limbYaw;
      	sittingOffset = 0;
        headUpOffset = 0.0F; // head normal
        toePointOffset = 0.0F;

		if (entitypenguin.isSitting()) // sitting
        {
        	sittingOffset = 1;
            body.rotateAngleX = 0.0F; // not leaning forward (LB, YE, and Mag)
        	rightfoot.rotateAngleX = pi * 1.5F;
        	leftfoot.rotateAngleX = pi * 1.5F;
	        rightwing.rotateAngleX = 0.0F;
        	rightwing.rotateAngleY = 0.0F;
        	rightwing.rotateAngleZ = 0.0F;
        } else { // not sitting
            if(entitypenguin.isInWater()){ // swimming, so horizontal and flap
            	body.rotateAngleX = 7 * pi / 16; // almost flat for swimming
                rightwing.rotateAngleX = -1.0F * pi / 2.0F;
                rightwing.rotateAngleY = MathHelper.abs(cosHalff * 1.4F) * pi / 2.5F; // flapping wings
                rightwing.rotateAngleZ = 0.0F;
                headUpOffset = -1.0F * pi / 3.0F; // head up
                // point the toes
                toePointOffset = -1.0F * pi / 4.0F;
            } else { // on land
                body.rotateAngleX = bodyLean; // leaning forward (LB, YE, and Mag)
		        rightwing.rotateAngleX = 0.0F;
		        if (!entitypenguin.onGround || MathHelper.abs((float)entitypenguin.motionY) > 0.1F) // || entitypenguin.isAirBorne) <= Most of the time!
		        {
			        rightwing.rotateAngleY = pi / 3.0F;
			        rightwing.rotateAngleZ = pi / 2.5F;
		        } else { // not jumping (or sitting)
			        if(speedSum < 0.2F){ // standing or slow walk
				        rightwing.rotateAngleY = 0.0F;
				        rightwing.rotateAngleZ = MathHelper.abs(cosf * f1 * 0.2F) + pi / 32.0F;
			        } else { // faster walk
				        rightwing.rotateAngleY = pi / 3.0F;
				        rightwing.rotateAngleZ = MathHelper.abs(cosf * f1 * 0.2F) + pi / 8.0F;
			        }
		        }
	        }
         	rightfoot.rotateAngleX = MathHelper.cos(f * 3.0F + pi) * 1.4F * f1 - bodyLean - toePointOffset;
        	leftfoot.rotateAngleX = MathHelper.cos(f * 3.0F) * 1.4F * f1 - bodyLean - toePointOffset;
	    }
	    leftwing.rotateAngleX = rightwing.rotateAngleX;
        leftwing.rotateAngleY = -1.0F * rightwing.rotateAngleY;
        leftwing.rotateAngleZ = -1.0F * rightwing.rotateAngleZ;
        
	    body.setRotationPoint(0F, bodyY + sittingOffset, 0F);

        float f3 = entitypenguin.getInterestedAngle(f2) + entitypenguin.getShakeAngle(f2, 0.0F);
        headmain.rotateAngleZ = f3;
        if(entitypenguin.getPenguinShaking())
        {
            float f4 = entitypenguin.getBrightness(f2) * entitypenguin.getShadingWhileShaking(f2);
            GL11.glColor3f(f4, f4, f4);
        }
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        headmain.rotateAngleX = par5 / 57.29578F - bodyLean + headUpOffset;
        headmain.rotateAngleY = par4 / 57.29578F;
    }
}