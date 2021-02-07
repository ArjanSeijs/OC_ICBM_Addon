## ICBM OpenComputers
[ICBM-Classic](https://www.curseforge.com/minecraft/mc-mods/icbm-classic) API integration for [OpenComputers](https://www.curseforge.com/minecraft/mc-mods/opencomputers)

## Wiki
See the [wiki](https://github.com/ArjanSeijs/OC_ICBM_Addon/wiki) for a more detailed explanation.

## Components
You can access the component by placing an [adapter](https://ocdoc.cil.li/block:adapter)  next to one of the following blocks:
- Missile Launcher
- Radar Station
- Cruise Launcher
- EMP Station

See the code snippets below for what functionality you can use.

### Accesing the components
By address

```lua
local component = require("component")
local proxy = component.proxy("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")
```

By searching through the components on component_name
```lua
local component = require("component")
local proxy = nil

-- Possible values: 
-- "component_missile_launcher", 
-- "component_emp_tower",
-- "component_radar_station", 
-- "component_cruise_launcher"
local component_name = "component_missile_launcher" 

for k,v in component.list() do
  local p = component.proxy(k)
  if not (p.isICBM == nil) and p.isICBM(component_name) then
    proxy = p
  end
end
```
 
## Know issues
- Missile launcher adapter changes address after save reload.



## Code Snippets
<details>
  <summary>Missile Launcher</summary> 
 
```lua
local serialization = require("serialization")
local component = require("component")
local adress = "xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" -- Put adress of the launcher here
 
-- Get the Launcher Component
local proxy = component.proxy(adress)
proxy.setTargetPos(math.random(100) + 320, math.random() + 350)
proxy.setDetonationHeight(math.random(150))
proxy.setFrequency(math.random(100))
proxy.setLockHeight(math.random(100))
 
print("Target location set to:")
print(serialization.serialize(proxy.getTargetPos()))
 
print("Target height set to:")
--print(proxy.getDetonationHeight())
 
print("Lock height is set to:")
print(proxy.getLockHeight())
 
print("Missile inaccuaracy is:")
print(proxy.getInaccuary())
 
print("Missile frequency is set to")
print(proxy.getFrequency())
 
if proxy.containsMissile() then
  print("Missile found, launching missile!")
  print(proxy.launchMissile()) -- Wil return true if sucess false otherwise
else
  print("Missile silo empty!")
end
```

</details>

<details>
  <summary>Radar Station</summary> 

```lua
local serialization = require("serialization")
local component = require("component")
local adress = "xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" -- Put adress of the radar station here

-- Get the proxy
local proxy = component.proxy(adress)
proxy.setAlarmRange(math.random(100)+400)
proxy.setSafetyRange(math.random(100)+300)
proxy.setFrequency(math.random(100))
 
print("The alarm range is")
print(proxy.getAlarmRange())
 
print("The safety range is")
print(proxy.getSafetyRange())
 
print("The frequency is")
print(proxy.getFrequency())
 
print("Incoming missiles")
print(serialization.serialize(proxy.getIncomingMissiles())) 
-- {
--  {uuid:"xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", x: 10 y: 100, z : 10},
--  {uuid:"xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", x: 20 y: 140, z : 20}
-- }
```

</details>

<details>
  <summary>EMP Tower</summary> 
 
```lua
local serialization = require("serialization")
local component = require("component")
local adress = "xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" -- Put adress of the EMP Tower here
 
-- Get the proxy
local proxy = component.proxy(adress)
proxy.setRadius(math.random(200)+300)
proxy.setMode(math.random(4)-1)
 
print("The current mode is:")
print(proxy.getMode())
 
print("The current radius is:")
print(proxy.getRadius())
 
print("Activating the emp:")
if proxy.isReady() then
    print(proxy.launch())
end
```

</details>

<details>
  <summary>Cruise Launcher</summary> 
 
```lua
local serialization = require("serialization")
local component = require("component")
local adress = "xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" -- Put adress of the cruise launcher here
       
-- Get the proxy
local proxy = component.proxy(adress)
proxy.setTargetPos(math.random(100) + 320, math.random(20) + 70, math.random(100) + 400)
proxy.setFrequency(math.random(100))
 
print("Target location set to:")
print(serialization.serialize(proxy.getTargetPos()))
 
print("The frequency is")
print(proxy.getFrequency())
 
os.sleep(3)
 
if proxy.canLaunch() then
  print("Can launch mssile!")
  print(proxy.launch())
else
  print("Missile cannot be lanched")
end
```

</details>

## Simple defense system
This is a simple system that will send an event when a missile has been detected and then will launch an anti-ballistic missile.
With this setup the anti ballistic missile will hit about 25% of the targets depending on location where it is fired from.

<details>
  <summary>radar.lua</summary> 
 
```lua
local serialization = require("serialization")
local component = require("component")
local comp = require("computer")
local thread = require("thread")
local table = require("table")
 
local adress = "xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" -- Put adress of the radar station here
 
-- Get the Launcher Component
local radar = component.proxy(adress)
local detected_missiles = {}
 
local function contains(uuid, list)
  for index, value in ipairs(list) do
    if value.UUID == uuid then
      return true
    end
  end
  return false
end
 
local function loop()
  local missiles = radar.getMissiles()
  local new_missiles = {}
   
  for index, value in ipairs(missiles) do
    if detected_missiles[value.UUID] == nil then
      detected_missiles[value.UUID] = value
      table.insert(new_missiles, value)
      comp.pushSignal("missile_detected", value)
    end
  end
   
  for key, detected_missile in pairs(detected_missiles) do
    if not contains(key, missiles) then
      detected_missiles[key] = nil
      comp.pushSignal("missile_lost", key)
    end
  end
   
end
 
local t = thread.create(function(a, b)
  while true do
    loop()
    os.sleep()
  end
end)
t:detach()
```
</details>

<details>
  <summary>anti_missile_launcher.lua</summary> 

```lua
local serialization = require("serialization")
local component = require("component")
local event = require("event")

local adress = "xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" -- Put adress of the missile launcher here
 
-- Get the proxy
local proxy = component.proxy(adresses.missile_launcher)
 
print("Waiting for incoming missiles")
while true do
  local _, missile = event.pull("missile_detected")
  print("Found missile: " .. serialization.serialize(missile))
  proxy.setDetonationHeight(missile.y)
  proxy.launch(missile.x, missile.z)
  event.pull("missile_lost")
  os.sleep()
end
```
</details>
