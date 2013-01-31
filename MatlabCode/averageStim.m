
timestep = time(2) - time(1);
length = 10;
steps = round(length/timestep);

stims = [];

for t = turnTimes'

    % Find start point p
    endStim = find(time == t);
    startStim = endStim - steps;
      
    stims = [stims; data(startStim:endStim,2)'];

end

figure; hold on;
plot(-length:timestep:0,mean(stims));
%plot(-length:timestep:0,mean(stims)-std(stims)/11,'--r');
%plot(-length:timestep:0,mean(stims)+std(stims)/11,'--r');

xlabel('Time (s)');
ylabel('Perception');