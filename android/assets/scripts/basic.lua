--
-- Created by IntelliJ IDEA.
-- User: Alumno
-- Date: 28/10/2015
-- Time: 10:47 AM
-- To change this template use File | Settings | File Templates.
--

--
-- Consideraciones ia
-- ->Si hay vida ataca, si no cubre
-- ->Excepcion: BERSEKER
-- ->Berseker ataca a lo wey
-- ->Behavior dependiente del arma
-- ->LowHealth=Cubre
--       ->MinimumHelath=Corre
--        ->Bosses atacan a lo verga inteligente
-- ->Bosses spawnean en checks
-- ->Spawneados en checks shielded
--

function senseSpawnAreas()

end

function evaluation()

end

function evaluateComplexSituation(me,player,gamestatus)

end

function selectStrategy(me,player)

end

function behavior(chrtr)
    evaluateDir(chrtr)
    chrtr:jump()
end

function evaluateDir(chrtr)
    plyr = chrtr:getPlay():getPlayer()
    if plyr:getDirection()then
        chrtr:setDirection(false)
    else
        chrtr:setDirection(true)
    end
   -- return chrtr
end

function attackOrHide(me,player,gamestate)
    if me:getHP() > 10 and canPlay(gamestate) and playerInRange(me,player) then
        return true
    end
    return false
end

function canPlay(gamestate)
    if gamestate == 1 or gamestate == 5 then
        return true
    end
    return false
end

function playerInRange(player)
    return true
end