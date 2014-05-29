%%

saveDir = 'D:\Dropbox\Uni\PhD\ChemotaxisFigures\';

names = {

'cast_turn_wv'
'cast_turn'
'cast_wv'
'turn_wv'
'cast'
'turn'
'wv'
'random'

};

linearNames = {
	'cast_turn_wv_exp'
	'cast_turn_wv_steep'
	'cast_turn_wv_shallow'
};

num = length(names);

numLinear = length(linearNames);

% Get stats

for i = 1:num
	
	name = names{i};
	
	disp(name);
	
	[stats{i}, variances{i}] = getMultiStats(name,500);

	
end

for i = 1:numLinear
	name = linearNames{i};
	disp(name);
	[linearStats{i}, lineaVariances{i}] = getMultiStats(name,500);
end

larvaStats = getLarvaStats();


%% Figure 1
% Heatmaps of all conditions + dist to odour source

figure; hold all;
for i = 1:length(names)

	subplot_tight(1,num,i);
	heatmapWithPaths(stats{i}.data.paths,[-32.25,32.25],[-50.65,50.65],1);
	title(names{i},'Interpreter','none');
	
end
set(gcf,'position',[76 345 1200 221]);

saveeps(saveDir,'Fig1_Heatmaps');

odourPeak = [-45.5 46.5].*0.1;

figure; hold all;
for i = 1:num
	meanDist(i,:) = distToOdour(stats{i}.data.paths,odourPeak);
	plot(10:10:300,meanDist(i,:));
end
ylim([0 30]);
legend(names,'Interpreter','none');

saveeps(saveDir,'Fig1_Distances');



%% Figure 2

for i = 1:num
	
	plotStats(stats{i},['D:\Dropbox\Uni\PhD\ChemotaxisFigures\' name '\']);	
	
	close all;
end


plotStats(larvaStats,'D:\Dropbox\Uni\PhD\ChemotaxisFigures\larva\');
close all;


%% Figure 3

% ha = tight_subplot(1,3);
for i = 1:numLinear
	% axes(ha(i));
	subplot_tight(1,numLinear,i);
	heatmapWithPaths(linearStats{i}.data.paths,[-50.2,50.2],[-32,32],1);
	title(linearNames{i},'Interpreter','none');
end

set(gcf,'position',[315 272 910 224]);

saveeps(saveDir,'Fig2_Heatmaps');

%% 

% % models,conditions,paths
% paths{1,1} = linearStats{1}.data.paths;
% paths{1,2} = linearStats{2}.data.paths;
% paths{1,3} = linearStats{3}.data.paths;
% clear paths;
% paths(1,:,:) = [linearStats{1}.data.paths; linearStats{2}.data.paths; linearStats{3}.data.paths];

% NB assumes order exp, steep, shallow
paths = getLinearPaths(linearNames);

compareLinearPaths(paths,1:2,100,90);


