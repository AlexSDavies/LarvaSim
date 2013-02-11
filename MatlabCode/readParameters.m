% Reads parameters from a file
% (Assuming parameters stored as 'name val' on each line)
function parameters = readParameters(fileName)

file = fopen(['../Data/' fileName]);
fileContents = textscan(file,'%s %f');
fclose(file);

names = fileContents{1};
vals = fileContents{2};

% Add all parameters to struct
% With name of parameter as field name
for i = 1:length(names); 
    parameters.(names{i}) = vals(i);
end