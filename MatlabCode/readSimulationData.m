function data = readSimulationData(dataName,turnsName)

f = fopen(dataName);
headings = fgetl(f);
fclose(f);

data = dlmread(dataName,' ',1,0);

time = data(:,1);


turnIndeces = dlmread(turnName,' ',0,0);
turnStartIndeces = turnIndeces(:,1);
turnEndIndeces = turnIndeces(:,2);

turnStarts = data(turnStartIndeces,:);
turnEnds = data(turnEndIndeces,:);
turnTimes = turnStarts(:,1);

turnCount = length(turnIndeces);





