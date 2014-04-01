function indivPIs = comparePIs(uniqueName,fileIndeces,numLarvae,parameterName)

indivPIs = zeros(numLarvae,length(fileIndeces));

h = waitbar(0,'Processing...');

for i  = 1:length(fileIndeces)
	f = fileIndeces(i);
	% parameters(f) = readParameters(['parameters_' uniqueName num2str(f) '_Larva_1']);
	indivPIs(:,i) = getPI([uniqueName num2str(f) '_'],numLarvae);
	% endPIs(:,f) = getEndPI([uniqueName num2str(f) '_'],numLarvae/10,10);
	waitbar(f/length(fileIndeces));
end

close(h);

% boxplot(PIs,[parameters.(parameterName)]);
% figure; hold on;
% boxplot(indivPIs);
% plot(xlim,[0,0],'-k');

% figure; hold on;
% boxplot(endPIs);
% plot(xlim,[0,0],'-k');
