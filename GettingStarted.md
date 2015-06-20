# Running the Demo via Maven #
For the moment running the demo is bound to the _exec:exec_ goal:
```
mvn test-compile exec:exec
```

# Loading an animated Quake III player model #
Load the model from the default resource path (`/res/q3/models/players/...`):
```
Q3Player player = new Q3Player("Chaos-Marine");
```
Set the upper and lower animation sequence:
```
player.setUpperSequence(AniCfgQ3Upper.STAND);           
player.setLowerSequence(AniCfgQ3Lower.IDLE);
```
Add the model to a jPCT world:
```
player.addTo(world);
```
Add the model to the scene root:
```
sceneRoot.addChild(player);
```
Configure the camera to look at the model:
```
world.getCamera().setPosition(100, 0, 0);
world.getCamera().lookAt(player.getTransformedCenter());
```
That's it.

In order for the animation to play you will need to continuously update the model in your main rendering loop:
```
player.aniTick(System.currentTimeMillis());
```