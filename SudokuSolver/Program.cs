
using SudokuSolver;
using SudokuSolver.Domain;

List<string> initialValues = new()
{
    "0,1,3,0,8,5,0,0,6",
    "0,0,0,0,3,1,0,0,0",
    "0,0,7,6,0,0,0,0,0",
    "0,0,6,9,0,0,0,0,3",
    "1,7,0,0,0,0,0,2,5",
    "3,0,0,0,0,6,9,0,0",
    "0,0,0,0,0,2,4,0,0",
    "0,0,0,8,7,0,0,0,0",
    "9,0,0,5,6,0,3,1,0"
};

Field inputField = new();
inputField.Init(initialValues);

Solver solver = new();
var outputField = solver.Solve(inputField);

Console.WriteLine(outputField);

Console.Read();