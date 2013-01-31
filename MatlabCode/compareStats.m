function simParams = compareStats()

larvaStats = getLarvaStats();

larvaParams = getStatParams(larvaStats);

for fileNum = 1:10

   disp(['Reading file ' num2str(fileNum)]);
    
   dataName = ['data' num2str(fileNum)];
   turnName = ['turns' num2str(fileNum)];
    
   simStats = getSimStats(dataName,turnName,0.1);
    
   simParams(fileNum) = getStatParams(simStats);

end

figure; hold on;

plot([0.3 3],[larvaParams.beforeLeftMean larvaParams.beforeLeftMean],'--m');
h1 = plot([0.3:0.3:3],[simParams.beforeLeftMean],'m');

plot([0.3 3],[larvaParams.afterLeftMean larvaParams.afterLeftMean],'--r');
h2 = plot([0.3:0.3:3],[simParams.afterLeftMean],'-r');

plot([0.3 3],[larvaParams.beforeRightMean larvaParams.beforeRightMean],'--g');
h3 = plot([0.3:0.3:3],[simParams.beforeRightMean],'-g');

plot([0.3 3],[larvaParams.afterRightMean larvaParams.afterRightMean],'--b');
h4 = plot([0.3:0.3:3],[simParams.afterRightMean],'-b');

legend([h1 h2 h3 h4],'Before left', 'After left', 'Before right', 'After right');

xlabel('Head cast kernal value');
ylabel('Bearing');