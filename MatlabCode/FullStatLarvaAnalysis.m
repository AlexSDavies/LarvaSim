
concs = [5000 500 50 5];

for c = 1:4
	
	conc = concs(c)
	
	[meanStats vars] = getMultiStats(['FullStatLarva_1_' int2str(conc) '_'],50);
	
	figure;
	positionHeatMap(meanStats.data.midPos,[-50 50],[-50 50],2);
	title(['1:' int2str(conc)]);
	
	saveeps('D:\Dropbox\Uni\PhD\ReviewFigures\FullStatLarva\',['Heatmap1-' num2str(conc)]);
	
end