
for (int row = 0; row < 9; row++)
    for (int col = 0; col < 9; col++)
    {
        int block = 3 * (row / 3) + (col / 3);
        Console.WriteLine($"({row},{col}) => {block}");
    }
