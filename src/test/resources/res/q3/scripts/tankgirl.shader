models/players/tankgirl/bat_goggles
{
	cull none
	{
		map models/players/tankgirl/specmap.tga
		tcGen environment
		rgbGen lightingDiffuse
	}
	{
	map models/players/tankgirl/bat_goggles.tga
		rgbGen lightingDiffuse
		blendfunc GL_ONE GL_SRC_ALPHA
        }
}

models/players/tankgirl/head_rocketbra
{
	cull none
	{
		map models/players/tankgirl/specmap.tga
		tcGen environment
		rgbGen lightingDiffuse
	}
	{
	map models/players/tankgirl/head_rocketbra.tga
		rgbGen lightingDiffuse
		blendfunc GL_ONE GL_SRC_ALPHA
        }
}

models/players/tankgirl/lower
{
	{
		map models/players/tankgirl/specmap.tga
		tcGen environment
		rgbGen lightingDiffuse
	}
	{
	map models/players/tankgirl/lower.tga
		rgbGen lightingDiffuse
		blendfunc GL_ONE GL_SRC_ALPHA
        }
}

models/players/tankgirl/pauldron
{
	{
	map models/players/tankgirl/specmap.tga
		tcGen environment
		rgbGen lightingDiffuse
	}
	{
	map models/players/tankgirl/pauldron.tga
		rgbGen lightingDiffuse
		blendfunc GL_ONE GL_SRC_ALPHA
        }
}

models/players/tankgirl/h_tekkgirl
{

	 {
		map models/players/tankgirl/h_tekkgirl.tga
		alphaFunc GE128
		depthWrite
		rgbGen lightingDiffuse
	}

}

models/players/tankgirl/invisible
{

	 {
		map models/players/tankgirl/invisible.tga
		alphaFunc GE128
		depthWrite
		rgbGen lightingDiffuse
	}

}

models/players/tankgirl/lower_tekkgirl
{

	{
		map models/players/tankgirl/envmapglass.tga
		tcGen environment
		rgbGen lightingdiffuse
	}
	{
		map models/players/tankgirl/lower_tekkgirl.tga
		blendFunc GL_ONE GL_SRC_ALPHA
		rgbGen lightingDiffuse
	}

}

models/players/tankgirl/upper_tekkgirl
{

	{
		map models/players/tankgirl/envmapglass.tga
		tcGen environment
		rgbGen lightingdiffuse
	}
	{
		map models/players/tankgirl/upper_tekkgirl.tga
		blendFunc GL_ONE GL_SRC_ALPHA
		rgbGen lightingDiffuse
	}

}