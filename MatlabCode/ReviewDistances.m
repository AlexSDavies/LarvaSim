names = {
'ReviewNormal'	
'ReviewVeryHighCastKernel'
'ReviewHighCastKernel'
'ReviewLowCastKernel'
'ReviewVeryLowCastKernel'
'ReviewHighCastBase'
'ReviewLowCastBase'
'ReviewHighTurnKernel'
'ReviewLowTurnKernel'
'ReviewNoTurnKernel'
'ReviewNoCastKernel'

'ReviewNewLarva'	
'ReviewNewLarvaHighCastKernel'
'ReviewNewLarvaLowCastKernel'
'ReviewNewLarvaHighCastBase'
'ReviewNewLarvaLowCastBase'
'ReviewNewLarvaHighTurnKernel'
'ReviewNewLarvaLowTurnKernel'
'ReviewNewLarvaNoTurnKernel'
'ReviewNewLarvaNoCastKernel'

};

for i = 1:length(names)
	
	name = names{i};
	
	disp(name);
	
	[meanStats variances] = getMultiStats(name,50);
	
	distanceTics = 5:10:95;
	bar(distanceTics,meanStats.distanceHist,1);
	hold on; errorbar(distanceTics,meanStats.distanceHist,sqrt(variances.distanceHist/variances.n),'r','LineStyle','none');
	ylim([0 1]);
	xlabel('Distance to odour source (mm)'); ylabel('Proportion of time');
	
	filename = ['D:\Dropbox\Uni\PhD\Review Figures\' name '_Distance.png'];
	export_fig(filename,'-transparent');
	close(gcf);
	
end
